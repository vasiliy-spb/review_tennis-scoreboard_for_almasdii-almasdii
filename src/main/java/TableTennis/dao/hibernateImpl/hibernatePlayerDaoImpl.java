package TableTennis.dao.hibernateImpl;

import TableTennis.dao.PlayerDao;
import TableTennis.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class hibernatePlayerDaoImpl implements PlayerDao {
    private final SessionFactory sessionFactory;
    public hibernatePlayerDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

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
                            "FROM Player p WHERE p.name = :name", Player.class)
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }
}
