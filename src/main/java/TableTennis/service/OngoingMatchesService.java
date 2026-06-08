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
    private final Map<UUID, Match> currentMatches = new ConcurrentHashMap<>();
    private final MatchScoreMapper matchScoreMapper = new MatchScoreMapper();
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    private final MatchValidator validator;
    private final PlayerService playerService;
    private final TransactionManager transactionManager;

    public UUID createMatch(MatchRequest request) {
        return transactionManager.doInTransaction(()->{
            validator.validateNames(request.firstPlayerName(),request.secondPlayerName());

            Player firstPlayer = playerService.findByNameOrCreate(request.firstPlayerName());
            Player secondPlayer = playerService.findByNameOrCreate(request.secondPlayerName());

            log.debug("first player : {} , second player : {}",firstPlayer,secondPlayer);
            UUID uuid = UUID.randomUUID();
            Match match = new Match(uuid,firstPlayer,secondPlayer);
            currentMatches.put(uuid,match);
            return uuid;
        });
    }
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

    private void saveCurrentMatch(Match match,UUID matchId){
        MatchEntity matchEntity = new MatchEntity(match.getFirstPlayer()
                ,match.getSecondPlayer()
                ,match.getWinner());

        log.debug("Match is saving : {}",matchEntity);

        finishedMatchesPersistenceService.save(matchEntity);
        currentMatches.remove(matchId);
    }

    public boolean wonPoint(UUID matchId, String playerName) {
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

