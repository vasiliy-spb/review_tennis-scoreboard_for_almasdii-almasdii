package TableTennis.dao.hibernateImpl;

import TableTennis.dao.PlayerDao;
import TableTennis.entity.Player;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Optional;

@RequiredArgsConstructor
public class hibernatePlayerDaoImpl implements PlayerDao {
    private final SessionFactory sessionFactory;
    public static final String FIND_BY_NAME = "SELECT p FROM Player p WHERE p.name = :name";

    @Override
    public Player save(Player entity) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(entity);

            transaction.commit();
            return entity;
        }
    }

    public Optional<Player> findByName(String name){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            FIND_BY_NAME, Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }
}
