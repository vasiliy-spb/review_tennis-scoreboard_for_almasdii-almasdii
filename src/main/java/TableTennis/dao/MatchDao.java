package TableTennis.dao;

import TableTennis.entity.MatchEntity;
import java.util.List;

public interface MatchDao extends Dao<MatchEntity> {
    List<MatchEntity> findAllMatches(int pageNumber,int pageSize);
    List<MatchEntity> findAllMatchesLikeName(int pageNumber,int pageSize,String name);
    Long totalNumberOfMatches();
}

