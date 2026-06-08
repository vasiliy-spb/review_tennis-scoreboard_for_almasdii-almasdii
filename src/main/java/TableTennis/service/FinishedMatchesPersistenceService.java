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
    @Getter public static final int DEFAULT_PAGE_SIZE = 20;
    @Getter public static final int DEFAULT_PAGE_NUMBER = 0;
    private final MatchDao matchDao;
    private final TransactionManager transactionManager;
    private final MatchValidator validator = new MatchValidator();
    private final Mapper<PaginationDto, PaginationData> mapper = new PaginationDtoMapper();

    public void save(MatchEntity match) {
        transactionManager.doInTransaction(()->{
            matchDao.save(match);
        });
    }

    public PaginationDto findAll(String playerName, int pageNumber) {
        validator.validatePage(pageNumber);
        validator.validateFilterName(playerName);

        List<MatchEntity> matchEntities = transactionManager.doInTransaction(() -> {
            List<MatchEntity> matches;
            if (playerName == null) {
                matches = matchDao.findAllMatches(pageNumber, DEFAULT_PAGE_SIZE);
            } else {
                matches = matchDao.findAllMatchesLikeName(pageNumber, DEFAULT_PAGE_SIZE, playerName);
            }
            return matches;
        });
        PaginationData paginationData = new PaginationData(matchEntities,numberOfPages(),DEFAULT_PAGE_SIZE,pageNumber);
        return mapper.mapFrom(paginationData);
    }

    public int numberOfPages(){
        return transactionManager.doInTransaction(()->{
            return (int) Math.ceil(matchDao.totalNumberOfMatches()/(double)DEFAULT_PAGE_SIZE);
        });
    }
}


