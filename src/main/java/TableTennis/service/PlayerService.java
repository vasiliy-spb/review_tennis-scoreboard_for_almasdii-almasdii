package TableTennis.service;

import TableTennis.dao.PlayerDao;
import TableTennis.entity.Player;
import TableTennis.utils.TransactionManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerService {
    private final PlayerDao playerDao;
    private final TransactionManager transactionManager;

    public Player findByNameOrCreate(String name) {
        return playerDao.findByName(name)
                .orElseGet(() -> playerDao.save(new Player(name)));

    }
}
