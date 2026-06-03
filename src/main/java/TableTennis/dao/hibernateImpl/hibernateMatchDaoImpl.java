package TableTennis.dao.hibernateImpl;

import TableTennis.dao.MatchDao;
import TableTennis.entity.MatchEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class hibernateMatchDaoImpl implements MatchDao {
    private final SessionFactory sessionFactory;
    public static final String FIND_ALL_MATCHES = """
            SELECT m
            FROM MatchEntity m
            JOIN FETCH m.firstPlayer f
            JOIN FETCH m.secondPlayer s
            JOIN FETCH m.winner w
            """;

    public static final String FIND_ALL_MATCHES_BY_NAME = """
            SELECT m FROM MatchEntity m
            JOIN FETCH m.winner w
            JOIN FETCH m.firstPlayer f
            JOIN FETCH m.secondPlayer s
            WHERE
            w.name LIKE :name
            OR f.name LIKE :name
            OR s.name LIKE :name
            """;

    public static final String TOTAL_NUMBER_OF_MATCHES = """
            SELECT count(m) FROM MatchEntity m
            """;

    @Override
    public List<MatchEntity> findAllMatches(int pageNumber, int pageSize) {
        try (Session session = sessionFactory.openSession()) {
            Query<MatchEntity> query = session.createQuery(FIND_ALL_MATCHES, MatchEntity.class);
            query.setMaxResults(pageSize);
            query.setFirstResult(pageNumber * pageSize);
            return query.getResultList();
        }

    }

    @Override
    public List<MatchEntity> findAllMatchesLikeName(int pageNumber, int pageSize, String playerName) {
        try (Session session = sessionFactory.openSession()) {
            Query<MatchEntity> query = session.createQuery(FIND_ALL_MATCHES_BY_NAME, MatchEntity.class);
            query.setParameter("name", "%" + playerName + "%");
            query.setFirstResult(pageNumber);
            query.setMaxResults(pageSize);

            return query.getResultList();
        }
    }

    @Override
    public Long totalNumberOfMatches() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(TOTAL_NUMBER_OF_MATCHES, Long.class).getSingleResult();
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
