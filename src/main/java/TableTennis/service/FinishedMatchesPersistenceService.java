package TableTennis.service;

import TableTennis.dao.MatchDao;
import TableTennis.dto.MatchResponse;
import TableTennis.dto.MatchView;
import TableTennis.entity.MatchEntity;

import java.util.List;

public class FinishedMatchesPersistenceService {
    private final MatchDao matchDao;
    public FinishedMatchesPersistenceService(MatchDao dao) {
        this.matchDao = dao;
    }

    public void save(MatchEntity match) {
        matchDao.save(match);
    }

    public List<MatchResponse> findAll(int pageNumber,String playerName) {
        List<MatchView> matchViews = matchDao.findAllMatchesWithName(pageNumber,playerName);
        return matchViews.stream().map(matchView ->
                new MatchResponse(matchView.firstPlayerName(),
                        matchView.SecondPlayerName(),
                        matchView.winnerName())).toList();
    }
}
