package TableTennis.dao;

import TableTennis.entity.Player;

import java.util.Optional;

public interface PlayerDao extends Dao<Player> {
    Optional<Player> findByName(String name);
}
