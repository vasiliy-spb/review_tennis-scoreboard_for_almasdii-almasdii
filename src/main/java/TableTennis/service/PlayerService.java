package TableTennis.service;

import TableTennis.dao.PlayerDao;
import TableTennis.entity.Player;
import TableTennis.utils.TransactionManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerService {

    // TODO: Нет интерфейса для этого класса. (см. файл "service.md" в этом же пакете)

    private final PlayerDao playerDao;

    // Поле TransactionManager не используется в классе — его стоит удалить
    private final TransactionManager transactionManager;

    public Player findByNameOrCreate(String name) {
        return playerDao.findByName(name)
                .orElseGet(() -> playerDao.save(new Player(name)));

    }
}
