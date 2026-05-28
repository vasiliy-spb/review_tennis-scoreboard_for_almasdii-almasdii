package TableTennis.dao.hibernateImpl;

import TableTennis.dao.MatchDao;
import TableTennis.dto.MatchView;
import TableTennis.entity.MatchEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class hibernateMatchDaoImpl implements MatchDao {
    private final SessionFactory sessionFactory;

    public hibernateMatchDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<MatchView> findAllMatchesWithName(int pageNumber,String playerName) {
        try (Session session = sessionFactory.openSession()) {
            String hql = """
                        SELECT new TableTennis.dto.MatchView(
                            p1.name,
                            p2.name,
                            w.name
                        )
                        FROM MatchEntity m
                        JOIN Player p1 ON p1.id = m.firstPlayerId
                        JOIN Player p2 ON p2.id = m.secondPlayerId
                        JOIN Player w  ON w.id  = m.winnerId
                    """;

            return session.createQuery(hql, MatchView.class).getResultList();
        }
    }

    @Override
    public MatchEntity save(MatchEntity match) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            session.persist(match);
            transaction.commit();

            return match;
        }
    }

}
