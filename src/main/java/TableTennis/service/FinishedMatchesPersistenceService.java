package TableTennis.service;

import TableTennis.dao.MatchDao;
import TableTennis.dto.PaginationData;
import TableTennis.dto.PaginationDto;
import TableTennis.entity.MatchEntity;
import TableTennis.mapper.Mapper;
import TableTennis.mapper.PaginationDtoMapper;
import TableTennis.utils.TransactionManager;
import TableTennis.validator.MatchValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FinishedMatchesPersistenceService {

    // TODO: Нет интерфейса для этого класса. (см. файл "service.md" в этом же пакете)

    // Размер страницы и номер по умолчанию более уместно хранить в сервлете, так как в идеале он должен приходить с фронтенда.
    // А сервис должен принимать это значение в качестве аргумента в методы.

    // Для констант в этом классе достаточно видимости private.

    // Маппер и валидатор лучше внедрять через конструктор, а не создавать их экземпляры прямо при объявлении полей.

    // Валидация имени игрока и номера страницы происходит внутри сервисного слоя, а не на "входе" в приложение.
        // Это не соответствует принципу быстрого отказа ("Fail Fast"):
        // Проверку корректности данных, пришедших от пользователя, следует проводить как можно раньше.
        // Валидация на уровне сервлета позволяет немедленно прервать обработку некорректного запроса и вернуть клиенту ошибку `400 Bad Request`.
        // Текущий подход заставляет приложение выполнять лишнюю работу, передавая невалидные данные дальше в сервисный слой.
        // Стоит запускать логику валидации из сервлета.

    // Получение списка матчей и получение их количества происходит в разных транзакциях, что может приводить к несогласованным результатам.
        // В текущем проекте это не вызовет проблем, но вообще стоит собирать информацию в одной транзакции.

    @Getter
    public static final int DEFAULT_PAGE_SIZE = 20;
    @Getter
    public static final int DEFAULT_PAGE_NUMBER = 0;
    private final MatchDao matchDao;
    private final TransactionManager transactionManager;
    private final MatchValidator validator = new MatchValidator();
    private final Mapper<PaginationDto, PaginationData> mapper = new PaginationDtoMapper();

    public void save(MatchEntity match) {
        transactionManager.doInTransaction(() -> {
            matchDao.save(match);
        });
    }

    public PaginationDto findAll(String playerName, int pageNumber) {
        validator.validatePage(pageNumber);
        validator.validateFilterName(playerName);

        // Возможно, лучше читалось бы так:
        /*
        Supplier<List<MatchEntity>> foundMatches;
        if (playerName == null) {
            foundMatches = () -> matchDao.findAllMatches(pageNumber, DEFAULT_PAGE_SIZE);
        } else {
            foundMatches = () -> matchDao.findAllMatchesLikeName(pageNumber, DEFAULT_PAGE_SIZE, playerName);
        }
        List<MatchEntity> matches = transactionManager.doInTransaction(foundMatches);
        */

        List<MatchEntity> matchEntities = transactionManager.doInTransaction(() -> {
            List<MatchEntity> matches;
            if (playerName == null) {
                matches = matchDao.findAllMatches(pageNumber, DEFAULT_PAGE_SIZE);
            } else {
                matches = matchDao.findAllMatchesLikeName(pageNumber, DEFAULT_PAGE_SIZE, playerName);
            }
            return matches;
        });
        PaginationData paginationData = new PaginationData(matchEntities, numberOfPages(playerName), pageNumber, DEFAULT_PAGE_SIZE);
        return mapper.mapFrom(paginationData);
    }

    public int numberOfPages(String playerName) {
        return transactionManager.doInTransaction(() -> {
            if (playerName != null && !playerName.isEmpty()) {
                return (int) Math.ceil(
                        matchDao.totalNumberOfMatches(playerName)
                        / (double) DEFAULT_PAGE_SIZE
                );
            }
            return (int) Math.ceil(
                    matchDao.totalNumberOfMatches()
                    / (double) DEFAULT_PAGE_SIZE
            );
        });
    }
}


