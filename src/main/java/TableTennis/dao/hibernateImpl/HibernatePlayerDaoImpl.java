package TableTennis.dao.hibernateImpl;

import TableTennis.dao.PlayerDao;
import TableTennis.entity.Player;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

@RequiredArgsConstructor
public class HibernatePlayerDaoImpl implements PlayerDao {

    // Текст HQL запроса удобнее читать, когда он логично разбит на строки, даже если он короткий.
        // Для визуального разделения запросов на строки лучше использовать текстовые блоки

    // TODO: Тело каждого метода стоит обернуть в try-catch и отлавливать HibernateException или PersistenceException.
        // Слой DAO должен перехватывать специфичные для технологии исключения (например, `HibernateException`)
        // и оборачивать их в свои, более общие исключения слоя доступа к данным (например, `DataBaseException`).
        // Это скрывает детали реализации от верхних слоёв и делает их независимыми от деталей реализации DAO.

    private final SessionFactory sessionFactory;

    // Константы объявляются первыми (пишутся в самом верху) в классе.
    // Достаточно видимости private
    // Можно добавить суффикс '_QHL' или '_QUERY'
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
                    .uniqueResult()); // Можно использовать .uniqueResultOptional()

    }
}
