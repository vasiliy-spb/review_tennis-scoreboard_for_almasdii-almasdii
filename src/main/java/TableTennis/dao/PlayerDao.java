package TableTennis.dao;

import TableTennis.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PlayerDao implements Dao<Player> {
    private final SessionFactory sessionFactory;
    public PlayerDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Player> findAll() {
       try(Session session = sessionFactory.openSession()){
           Transaction transaction = session.beginTransaction();

           List<Player> players = session.createQuery("FROM Player",Player.class).getResultList();

           transaction.commit();
           return  players;
        }
    }

    @Override
    public Player save(Player enitity) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(enitity);

            transaction.commit();
            return enitity;
        }
    }


    @Override
    public Optional<Player> findById(int id) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Optional<Player> player = Optional.ofNullable(session.get(Player.class,id));
            transaction.commit();
            return player;
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
