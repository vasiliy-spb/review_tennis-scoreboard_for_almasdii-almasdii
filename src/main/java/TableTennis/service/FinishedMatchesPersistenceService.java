package TableTennis.service;

import TableTennis.dao.MatchDao;
import TableTennis.dto.MatchResponse;
import TableTennis.dto.MatchView;
import TableTennis.entity.MatchEntity;
import TableTennis.validator.MatchValidator;

import java.util.List;

public class FinishedMatchesPersistenceService {
    private final MatchDao matchDao;
    private final MatchValidator validator = new MatchValidator();
    public FinishedMatchesPersistenceService(MatchDao dao) {
        this.matchDao = dao;
    }

    public void save(MatchEntity match) {
        matchDao.save(match);
    }

    public List<MatchResponse> findAll() {
//        validator.validatePage(pageNumber,size);
        List<MatchView> matchViews = matchDao.findAllMatchesWithName();
        return matchViews.stream().map(matchView ->
                new MatchResponse(matchView.firstPlayerName(),
                        matchView.SecondPlayerName(),
                        matchView.winnerName())).toList();
    }
}
