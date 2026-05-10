package TableTennis.service;

import TableTennis.dao.PlayerDao;
import TableTennis.dto.MatchRequest;
import TableTennis.model.OngoingMatch;
import TableTennis.entity.Match;
import TableTennis.entity.Player;
import TableTennis.model.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OngoingMatchesService {
    private final PlayerDao playerDao;
    private final Map<UUID, OngoingMatch> currentMatches;
    private final MatchScoreCalculationService calculationService;
    private final FinishedMatchesPersistenceService finishedMatchesPersistenceService;
    public OngoingMatchesService(PlayerDao playerDao
            , MatchScoreCalculationService calculationService
            , FinishedMatchesPersistenceService finishedMatchesPersistenceService){
        this.playerDao = playerDao;
        this.calculationService = calculationService;
        this.finishedMatchesPersistenceService = finishedMatchesPersistenceService;
        currentMatches = new HashMap<>();
    }

    public UUID save(MatchRequest request1) {
        Player player1 = playerDao.findByName(request1.firstPlayerName())
                .orElseGet(
                        () -> playerDao.save(new Player(request1.firstPlayerName())));
        Player player2 = playerDao.findByName(request1.secondPlayerName())
                .orElseGet(
                        () -> playerDao.save(new Player(request1.secondPlayerName())));
        System.out.println(player1);
        System.out.println(player2);

        OngoingMatch match = OngoingMatch.builder()
                .firstPlayer(player1)
                .player2(player2)
                .firstPlayerPoints(Point.LOVE)
                .secondPlayerPoints(Point.LOVE)
                .build();
        UUID uuid = UUID.randomUUID();
        currentMatches.put(uuid,match);
        return uuid;
    }
    public OngoingMatch getById(UUID uuid){
        return currentMatches.get(uuid);
    }

    public void wonPoint(UUID matchId, String playerName) {
        OngoingMatch match = getById(matchId);
        calculationService.won(match,playerName);

        if(match.isFinished()){
            Match match1 = Match.builder()
                    .firstPlayerId(match.getFirstPlayer().getId())
                    .secondPlayerId(match.getPlayer2().getId())
                    .winnerId(match.getWinner().getId()).build();
            finishedMatchesPersistenceService.save(match1);
        }
    }
}
