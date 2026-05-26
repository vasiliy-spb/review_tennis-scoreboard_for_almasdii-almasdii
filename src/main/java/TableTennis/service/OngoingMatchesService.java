package TableTennis.service;

import TableTennis.dao.PlayerDao;
import TableTennis.dto.MatchRequest;
import TableTennis.entity.MatchEntity;
import TableTennis.model.Match;
import TableTennis.entity.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class OngoingMatchesService {
    private final PlayerDao playerDao;
    private final Map<UUID, Match> currentMatches;
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    public OngoingMatchesService(PlayerDao playerDao
            , FinishedMatchesPersistenceService finishedMatchesPersistenceService){
        this.playerDao = playerDao;
        this.finishedMatchesPersistenceService = finishedMatchesPersistenceService;
        currentMatches = new ConcurrentHashMap<>();
    }

    public UUID createMatch(MatchRequest request) {

        Player firstPlayer = playerDao.findByName(request.firstPlayerName())
                .orElseGet(
                        () -> playerDao.save(new Player(request.firstPlayerName())));

        Player secondPlayer = playerDao.findByName(request.secondPlayerName())
                .orElseGet(
                        () -> playerDao.save(new Player(request.secondPlayerName())));

        log.debug("first player : {} , second player : {}",firstPlayer,secondPlayer);

        UUID uuid = UUID.randomUUID();
        Match match = new Match(uuid,firstPlayer,secondPlayer);

        currentMatches.put(uuid,match);

        return uuid;
    }
    public Match getById(UUID uuid){
        return currentMatches.get(uuid);
    }
    public Optional<Player> getPlayerByName(String playerName){
        return playerDao.findByName(playerName);
    }

    public void wonPoint(UUID matchId, String playerName) {
        Player player = getPlayerByName(playerName).orElseThrow();
        Match match = getById(matchId);
        boolean isMatchEnd = match.pointWonBy(player);

        log.debug("point won player : {} , for match : {} ",player,match);

        if(isMatchEnd){
            MatchEntity matchEntity = new MatchEntity(match.getFirstPlayer().getId()
                    ,match.getSecondPlayer().getId()
                    ,match.getWinner().getId());

            log.debug("Match is saving : {}",matchEntity);

            finishedMatchesPersistenceService.save(matchEntity);
            currentMatches.remove(matchId);
        }
    }
}

