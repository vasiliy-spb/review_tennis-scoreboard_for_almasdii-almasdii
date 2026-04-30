package TableTennis.dao;

import TableTennis.dto.MatchView;
import TableTennis.entity.Match;
import TableTennis.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MatchDao implements Dao<Match> {
    private final SessionFactory sessionFactory;

    public MatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Match> findAll() {
        return null;
    }

    public List<MatchView> findAllMatchesName() {
        try (Session session = sessionFactory.openSession()) {
            String hql = """
                        SELECT new TableTennis.dto.MatchView(
                            p1.name,
                            p2.name,
                            w.name
                        )
                        FROM Match m
                        JOIN Player p1 ON p1.id = m.firstPlayerId
                        JOIN Player p2 ON p2.id = m.secondPlayerId
                        JOIN Player w  ON w.id  = m.winnerId
                    """;

            return session.createQuery(hql, MatchView.class).getResultList();
        }
    }

    @Override
    public Match save(Match match) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();
            session.persist(match);
            transaction.commit();
            return match;
        }
    }


    @Override
    public Optional<Match> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<Match> match = Optional.ofNullable(session.get(Match.class, String.valueOf(id)));
            session.getTransaction().commit();
            return match;
        }
    }

}
