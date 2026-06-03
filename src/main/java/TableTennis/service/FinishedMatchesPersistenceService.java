package TableTennis.service;

import TableTennis.dao.MatchDao;
import TableTennis.dto.MatchResponse;
import TableTennis.entity.MatchEntity;
import TableTennis.validator.MatchValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FinishedMatchesPersistenceService {
    @Getter public static final int DEFAULT_PAGE_SIZE = 20;
    @Getter public static final int DEFAULT_PAGE_NUMBER = 0;
    private final MatchDao matchDao;
    private final MatchValidator validator = new MatchValidator();

    public void save(MatchEntity match) {
        matchDao.save(match);
    }

    public List<MatchResponse> findAll(String playerName,int pageNumber) {
        validator.validatePage(pageNumber);
        validator.validateFilterName(playerName);
        List<MatchEntity> matches;
        if(pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if(playerName == null){
            matches = matchDao.findAllMatches(pageNumber,DEFAULT_PAGE_SIZE);
        }else {
            matches = matchDao.findAllMatchesLikeName(pageNumber,DEFAULT_PAGE_SIZE,playerName);
        }

        return matches.stream().map(match ->
                new MatchResponse(match.getFirstPlayer().getName(),
                        match.getSecondPlayer().getName(),
                        match.getWinner().getName())).toList();
    }

    public int numberOfPages(){
        int pages = (int) Math.ceil(matchDao.totalNumberOfMatches()/(double)DEFAULT_PAGE_SIZE)-1;
        log.debug("number of pages = {}",pages);
        return pages;
    }
}

