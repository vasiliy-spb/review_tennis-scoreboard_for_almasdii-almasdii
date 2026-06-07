package TableTennis.dao.hibernateImpl;

import TableTennis.dao.PlayerDao;
import TableTennis.entity.Player;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class HibernatePlayerDaoImpl implements PlayerDao {
    private final SessionFactory sessionFactory;
    public static final String FIND_BY_NAME = "SELECT p FROM Player p WHERE p.name = :name";

    @Override
    public Player save(Player entity) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(entity);
            return entity;

    }

    public Optional<Player> findByName(String name){
        Session currentSession = sessionFactory.getCurrentSession();
            return Optional.ofNullable(currentSession.createQuery(
                            FIND_BY_NAME, Player.class)
                    .setParameter("name", name)
                    .uniqueResult());

    }
}
