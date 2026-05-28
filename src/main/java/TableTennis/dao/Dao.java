package TableTennis.dao;

public interface Dao<K,ID> {
    K save(K entity);
}

