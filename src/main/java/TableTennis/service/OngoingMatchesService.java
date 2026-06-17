package TableTennis.service;

import TableTennis.Exception.BadRequestException;
import TableTennis.Exception.MatchNotFoundException;
import TableTennis.dto.MatchRequest;
import TableTennis.dto.MatchScoreDto;
import TableTennis.entity.MatchEntity;
import TableTennis.entity.Player;
import TableTennis.mapper.MatchScoreMapper;
import TableTennis.model.Match;
import TableTennis.utils.TransactionManager;
import TableTennis.validator.MatchValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class OngoingMatchesService {

    // TODO: Нет интерфейса для этого класса. (см. файл "service.md" в этом же пакете)

    // TODO: Класс нарушает Принцип единой ответственности (SRP).
        // Он выполняет несколько разных задач:
            // - управляет хранилищем текущих матчей
            // - оркестрирует бизнес-логику
            // - сам преобразует доменную модель Match в MatchEntity
        // Как исправить:
            // Ответственности можно было бы разделить на несколько более сфокусированных классов

    // Маппер лучше внедрять через конструктор, а не создавать экземпляр прямо при объявлении поля.
        // А также использовать интерфейс для типа этого поля.

    // Валидация имён игроков происходит внутри сервисного слоя, а не на "входе" в приложение.
        // Это не соответствует принципу быстрого отказа ("Fail Fast"):
        // Проверку корректности данных, пришедших от пользователя, следует проводить как можно раньше.
        // Валидация на уровне сервлета позволяет немедленно прервать обработку некорректного запроса и вернуть клиенту ошибку `400 Bad Request`.
        // Текущий подход заставляет приложение выполнять лишнюю работу, передавая невалидные данные дальше в сервисный слой.
        // Стоит запускать логику валидации из сервлета.

    // TODO: Класс отвечает за создание и хранение объекта текущего матча (доменной модели).
        // При этом он способствует смешению слоёв — работает с JPA Entity и передаёт их в доменную модель.
        // (см. файл "separation-of-concerns-principle.md" в этом же пакете)

    private final Map<UUID, Match> currentMatches = new ConcurrentHashMap<>();
    private final MatchScoreMapper matchScoreMapper = new MatchScoreMapper();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    private final MatchValidator validator;
    private final PlayerService playerService;
    private final TransactionManager transactionManager;

    public UUID createMatch(MatchRequest request) {
        return transactionManager.doInTransaction(()->{
            validator.validateNames(request.firstPlayerName(),request.secondPlayerName());

            // Нет необходимости выполнять валидацию в транзакции

            Player firstPlayer = playerService.findByNameOrCreate(request.firstPlayerName());
            Player secondPlayer = playerService.findByNameOrCreate(request.secondPlayerName());

            log.debug("first player : {} , second player : {}",firstPlayer,secondPlayer);
            UUID uuid = UUID.randomUUID();

            // Не стоит передавать JPA Entity в доменные модели
            Match match = new Match(uuid,firstPlayer,secondPlayer);
            currentMatches.put(uuid,match);
            return uuid;
        });
    }

    // Метод смешивает два подхода к обработке отсутствующего матча:
        // - сигнатура говорит, что он вернёт Optional
        // - код сам бросает исключение
        // Стоит выбрать один подход.
    public Optional<MatchScoreDto> getMatchScoreById(UUID uuid){
        if(uuid == null){
            throw new BadRequestException("Match UUid is null");
        }
        Match match = currentMatches.get(uuid);
        if(match == null){
            throw new MatchNotFoundException("Match is not found");
        }
        MatchScoreDto matchScoreDto = matchScoreMapper.mapFrom(match);
        return Optional.ofNullable(matchScoreDto);
    }

    // Метод нарушает SRP на уровне метода (сохраняет матч в БД и удаляет его из хранилища в памяти).
        // А также удаление матча их хранилища является в этом методе является неожиданным побочным эффектом
        // и нарушает принцип наименьшего удивления (см. файл "pola.md" в этом же пакете)
    private void saveCurrentMatch(Match match,UUID matchId){
        MatchEntity matchEntity = new MatchEntity(match.getFirstPlayer()
                ,match.getSecondPlayer()
                ,match.getWinner());

        log.debug("Match is saving : {}",matchEntity);

        finishedMatchesPersistenceService.save(matchEntity);
        currentMatches.remove(matchId);
    }

    // TODO: Race condition при обработке выигранного очка.
        // Если пользователь очень быстро нажмёт кнопку выигрыша очка, браузер отправит два POST-запроса почти одновременно.
        // Tomcat обработает эти два запроса в двух разных потоках, но так как оба потока будут работать с одним и тем же общим объектом `Match`,
        // будет возникать ситуация, когда счёт изменится только один раз.
        // Чтобы это исправить, нужно гарантировать, что только один поток может изменять состояние конкретного матча в один момент времени.
    public boolean wonPoint(UUID matchId, String playerName) {

        // Если игроки сохраняются в БД при создании матча, то во время начисления очка они оба уже должны существовать.
            // Поэтому логика должна была быть (только в текущей реализации) не "найти или создать", а "найти или бросить исключение".
        // TODO: Обращение в БД за игроком при каждом выигранном очке порождает избыточную нагрузку на БД.
            // За время одно матча будет выполнено как минимум 48 таких запросов.
        Player player = transactionManager.doInTransaction(() -> {
            return playerService.findByNameOrCreate(playerName);
        });

        Match match = currentMatches.get(matchId);
        if(match == null){
            throw new MatchNotFoundException("Match is not found");
        }
        boolean isMatchEnd = match.pointWonBy(player);
        log.debug("point won player : {} , for match : {} ",player,match);

        if(isMatchEnd){
            saveCurrentMatch(match,matchId);
        }
        return isMatchEnd;
    }
}

