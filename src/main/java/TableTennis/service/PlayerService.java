package TableTennis.service;

import TableTennis.dao.PlayerDao;
import TableTennis.entity.Player;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerService {
    private final PlayerDao playerDao;
    public Player findByNameOrCreate(String name){
        return playerDao.findByName(name)
                .orElseGet(() -> playerDao.save(new Player(name)));
    }
}
