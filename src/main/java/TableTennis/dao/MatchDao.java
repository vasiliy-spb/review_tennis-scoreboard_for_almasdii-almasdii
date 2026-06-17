package TableTennis.dao;

import TableTennis.entity.MatchEntity;

import java.util.List;

public interface MatchDao extends Dao<MatchEntity> {

    // Можно просто findAll
    // TODO: Метод DAO должен принимать уже готовые значения для смещения (offset — количество пропускаемых записей)
        // и limit (максимальное количество записей на странице — в текущей реализации — pageSize),
        // чтобы не заниматься их вычислениями самостоятельно
    List<MatchEntity> findAllMatches(int pageNumber,int pageSize);

    // Более понятным было бы findAllByPlayerName
    // TODO: Метод DAO должен принимать уже готовые значения для смещения (offset — количество пропускаемых записей)
        // и limit (максимальное количество записей на странице — в текущей реализации — pageSize),
        // чтобы не заниматься их вычислениями самостоятельно
    List<MatchEntity> findAllMatchesLikeName(int pageNumber,int pageSize,String name);

    // Можно просто countAll
    Long totalNumberOfMatches();

    // Больше подошло бы countByPlayerName
    Long totalNumberOfMatches(String playerName);
}

