package TableTennis.dao;

import TableTennis.dto.MatchView;
import TableTennis.entity.MatchEntity;
import java.util.List;

public interface MatchDao extends Dao<MatchEntity,Long> {
    List<MatchView> findAllMatchesWithName();
}

