package TableTennis.service;

import TableTennis.dao.PlayerDao;
import TableTennis.entity.Player;

import java.util.Optional;

public class PlayerService {
    private final PlayerDao playerDao;
    public PlayerService(PlayerDao playerDao){
        this.playerDao = playerDao;
    }
    public Player findByNameOrCreate(String name){
        return playerDao.findByName(name)
                .orElseGet(() -> playerDao.save(new Player(name)));
    }
}
