package TableTennis.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K> {
    List<K> findAll();
    K save(K enitity);
    Optional<K> findById(int id);

}

