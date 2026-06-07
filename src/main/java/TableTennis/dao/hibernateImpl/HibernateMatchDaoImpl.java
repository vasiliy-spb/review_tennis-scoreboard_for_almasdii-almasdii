package TableTennis.dao.hibernateImpl;

import TableTennis.dao.MatchDao;
import TableTennis.entity.MatchEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class HibernateMatchDaoImpl implements MatchDao {
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
        Session currentSession = sessionFactory.getCurrentSession();
        Query<MatchEntity> query = currentSession.createQuery(FIND_ALL_MATCHES, MatchEntity.class);
        query.setMaxResults(pageSize);
        query.setFirstResult(pageNumber * pageSize);
        log.debug("page size : {}",pageSize);
        log.debug("first result : {}",pageNumber * pageSize);
        return query.getResultList();

    }

    @Override
    public List<MatchEntity> findAllMatchesLikeName(int pageNumber, int pageSize, String playerName) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<MatchEntity> query = currentSession.createQuery(FIND_ALL_MATCHES_BY_NAME, MatchEntity.class);
        query.setParameter("name", "%" + playerName + "%");
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();

    }

    @Override
    public Long totalNumberOfMatches() {
        Session currentSession = sessionFactory.getCurrentSession();
        Long singleResult = currentSession.createQuery(TOTAL_NUMBER_OF_MATCHES, Long.class).getSingleResult();
        log.debug("total matches : {} ", singleResult);
        return singleResult;

    }

    @Override
    public MatchEntity save(MatchEntity match) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(match);
        return match;
    }

}
