package TableTennis.service;

import TableTennis.dao.MatchDao;
import TableTennis.dao.PlayerDao;
import TableTennis.model.OngoingMatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NewMatchService {
    private final MatchDao matchDao;
    private final PlayerDao playerDao;
    private final Map<UUID, OngoingMatch> currentMatches;

    public NewMatchService(MatchDao matchDao, PlayerDao playerDao) {
        this.matchDao = matchDao;
        this.playerDao = playerDao;
        currentMatches = new HashMap<>();
    }


}
