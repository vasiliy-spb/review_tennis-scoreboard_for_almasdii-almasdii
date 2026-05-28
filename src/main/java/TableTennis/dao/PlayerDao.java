package TableTennis.dao;

import TableTennis.entity.Player;
import java.util.Optional;

public interface PlayerDao extends Dao<Player,Long> {
    Optional<Player> findByName(String name);
}
