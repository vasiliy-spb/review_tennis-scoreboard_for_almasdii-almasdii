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

    // Константы объявляются первыми (пишутся в самом верху) в классе.

    // Для констант в этом классе достаточно видимости private.

    // Можно добавить суффикс '_QHL' или '_QUERY' к константам с текстом запросов.

    // Где-то SELECT и FROM написаны на одной строке, а где-то на разных — лучше использовать один подход.

    // В HQL запросах используется JOIN FETCH, что эквивалентно 'INNER JOIN' в SQL.
        //
        // `INNER JOIN` вернёт только те записи о матчах, у которых все связанные сущности (`player1`, `player2`)
        // гарантированно существуют в базе. Если по какой-либо причине (например, ошибка при импорте или
        // ручное вмешательство) в таблице `matches` окажется запись со значением `NULL` в колонке `player1`,
        // то такой матч будет молчаливо исключён из выборки.
        //
        // `LEFT JOIN` является более безопасным подходом:
            //  - Он вернёт все матчи, даже если у них нарушена связь с игроком.
            //  - Это позволит приложению либо упасть с `NullPointerException` (что явно укажет на проблему
                //  с целостностью данных), либо корректно обработать такую ситуацию, если она допустима.
                //  "Падать громко и рано" часто лучше, чем молча скрывать проблемы.
        //
        // Стоит заменить `JOIN FETCH` на `LEFT JOIN FETCH` для обоих игроков и победителя
        // для большей устойчивости запроса к потенциально некорректным данным.
        //
        // (см. файл "join-fetch-left-join-fetch.md" в этом же пакете)

    // TODO: В методах отсутствует явная сортировка результатов. Запросы HQL не содержат `ORDER BY`,
        // поэтому порядок возвращаемых записей зависит от реализации JPA (обычно по первичному ключу в порядке возрастания).
        // Это приводит к тому, что самые новые матчи отображаются в конце списка.
        // Пользователь, заходящий на страницу завершённых матчей, ожидает увидеть сначала последние завершённые матчи.
        // В текущей реализации ему приходится пролистывать пагинацию до конца, чтобы найти свежие результаты.
        // Это ухудшает пользовательский опыт и делает интерфейс неинтуитивным.
        // При большом количестве матчей добираться до новых данных будет крайне неудобно.
        // Стоит добавить в HQL-запрос сортировку по убыванию идентификатора матча, так как это естественный способ упорядочить матчи от новых к старым.

    // Из HQL-запросов можно убрать проверку совпадения по имени победителя —
        // так как он всегда является одним из игроков, достаточно проверять совпадения только по их именам.

    // Название параметра "name" тоже лучше вынести в именованную константу

    // TODO: Тело каждого метода стоит обернуть в try-catch и отлавливать HibernateException или PersistenceException.
        // Слой DAO должен перехватывать специфичные для технологии исключения (например, `HibernateException`)
        // и оборачивать их в свои, более общие исключения слоя доступа к данным (например, `DataBaseException`).
        // Это скрывает детали реализации от верхних слоёв и делает их независимыми от деталей реализации DAO.

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
    public static final String TOTAL_NUMBER_OF_ALL_MATCHES_BY_NAME = """
        SELECT count(m) FROM MatchEntity m
        JOIN m.winner w
        JOIN m.firstPlayer f
        JOIN m.secondPlayer s
        WHERE
            w.name LIKE :name
            OR f.name LIKE :name
            OR s.name LIKE :name
        """;

    @Override
    public List<MatchEntity> findAllMatches(int pageNumber, int pageSize) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<MatchEntity> query = currentSession.createQuery(FIND_ALL_MATCHES, MatchEntity.class);
        query.setMaxResults(pageSize);

        // Заниматься вычислением смещения должен слой сервисов. (см. файл "separation-of-concerns-principle.md" в этом же пакете)
        query.setFirstResult(pageNumber * pageSize);

        // Логировать параметры следует там, где принимается решение и где есть полный контекст.
            // В данном случае, решение о том, какую страницу запросить и с каким размером, принимается на уровне сервиса.
            // А также класс DAO не должен знать о таких понятиях как страницы или пагинация.
            // Ему следует передавать уже готовые параметры offset (смещение) и limit (лимит).
            // Поэтому логировать эти параметры лучше в сервисном слое.
        log.debug("page size : {}",pageSize);
        log.debug("first result : {}",pageNumber * pageSize);
        return query.getResultList();

    }

    @Override
    public List<MatchEntity> findAllMatchesLikeName(int pageNumber, int pageSize, String playerName) {
        Session currentSession = sessionFactory.getCurrentSession();

        // Можно записать в таком стиле:
        /*
        currentSession.createQuery(FIND_ALL_MATCHES_BY_NAME, MatchEntity.class)
                .setParameter("name", "%" + playerName + "%")
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
         */

        Query<MatchEntity> query = currentSession.createQuery(FIND_ALL_MATCHES_BY_NAME, MatchEntity.class);
        query.setParameter("name", "%" + playerName + "%");

        // Заниматься вычислением смещения должен слой сервисов. (см. файл "separation-of-concerns-principle.md" в этом же пакете)
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        return query.getResultList();

    }

    @Override
    public Long totalNumberOfMatches() {
        Session currentSession = sessionFactory.getCurrentSession();
        Long singleResult = currentSession
                .createQuery(TOTAL_NUMBER_OF_MATCHES, Long.class)
                .getSingleResult();

        log.debug("total matches : {} ", singleResult);
        return singleResult;
    }

    @Override
    public Long totalNumberOfMatches(String playerName) {
        Session currentSession = sessionFactory.getCurrentSession();

        return currentSession
                .createQuery(TOTAL_NUMBER_OF_ALL_MATCHES_BY_NAME, Long.class)
                .setParameter("name", "%" + playerName + "%")
                .getSingleResult();
    }

    @Override
    public MatchEntity save(MatchEntity match) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(match);
        return match;
    }

}
