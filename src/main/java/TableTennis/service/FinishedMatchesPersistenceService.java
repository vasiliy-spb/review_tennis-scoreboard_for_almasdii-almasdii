package TableTennis.service;

import TableTennis.dao.MatchDao;
import TableTennis.dao.PlayerDao;
import TableTennis.dto.MatchResponse;
import TableTennis.dto.MatchView;
import TableTennis.entity.MatchEntity;

import java.util.List;

public class FinishedMatchesPersistenceService {
    private final MatchDao matchDao;
    private final PlayerDao playerDao;

    public FinishedMatchesPersistenceService(MatchDao dao, PlayerDao playerDao) {
        this.matchDao = dao;
        this.playerDao = playerDao;
    }

    public void save(MatchEntity match1) {
        matchDao.save(match1);
    }

    public List<MatchResponse> findAll() {
        List<MatchView> matchViews = matchDao.findAllMatchesName();
        return matchViews.stream().map(matchView -> new MatchResponse(matchView.firstPlayerName(), matchView.SecondPlayerName(), matchView.winnerName())).toList();
    }
}
