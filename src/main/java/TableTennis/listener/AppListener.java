package TableTennis.listener;

import TableTennis.dao.MatchDao;
import TableTennis.dao.PlayerDao;
import TableTennis.dao.hibernateImpl.HibernateMatchDaoImpl;
import TableTennis.dao.hibernateImpl.HibernatePlayerDaoImpl;
import TableTennis.entity.Player;
import TableTennis.service.FinishedMatchesPersistenceService;
import TableTennis.service.OngoingMatchesService;
import TableTennis.service.PlayerService;
import TableTennis.utils.TransactionManager;
import TableTennis.validator.MatchValidator;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebListener
public class AppListener implements ServletContextListener {
    SessionFactory sessionFactory;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Player.class);
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();

        PlayerDao playerDao = new HibernatePlayerDaoImpl(sessionFactory);
        MatchDao matchDao = new HibernateMatchDaoImpl(sessionFactory);

        TransactionManager transactionManager = new TransactionManager(sessionFactory);


        FinishedMatchesPersistenceService finishedMatchesPersistenceService =
                new FinishedMatchesPersistenceService(matchDao,transactionManager);

        MatchValidator validator = new MatchValidator();
        PlayerService playerService = new PlayerService(playerDao,transactionManager);

        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService(
                finishedMatchesPersistenceService,
                validator,
                playerService,
                transactionManager);

        sce.getServletContext().setAttribute("OngoingMatchesService", ongoingMatchesService);
        sce.getServletContext().setAttribute("FinishedMatchesPersistenceService", finishedMatchesPersistenceService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);

        sessionFactory.close();
    }
}
