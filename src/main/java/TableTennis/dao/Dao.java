package TableTennis.dao;

public interface Dao<K> {

    // В этом проекте можно не вводить базовый интерфейс ради одного метода

    // Вместо K в качестве параметра типа больше подошла бы E (от Entity)

    K save(K entity);
}

