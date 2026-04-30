package TableTennis.listener;

import TableTennis.dao.MatchDao;
import TableTennis.dao.PlayerDao;
import TableTennis.entity.Player;
import TableTennis.service.FinishedMatchesPersistenceService;
import TableTennis.service.MatchScoreCalculationService;
import TableTennis.service.NewMatchService;
import TableTennis.service.OngoingMatchesService;
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

        PlayerDao playerDao = new PlayerDao(sessionFactory);
        MatchDao matchDao = new MatchDao(sessionFactory);
        FinishedMatchesPersistenceService finishedMatchesPersistenceService =
                new FinishedMatchesPersistenceService(matchDao,playerDao);
        MatchScoreCalculationService matchScoreCalculationService =
                new MatchScoreCalculationService();
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService(
                playerDao,
                matchScoreCalculationService,
                finishedMatchesPersistenceService);
        NewMatchService newMatchService = new NewMatchService(matchDao, playerDao);
        sce.getServletContext().setAttribute("NewMatchService", newMatchService);
        sce.getServletContext().setAttribute("OngoingMatchesService", ongoingMatchesService);
        sce.getServletContext().setAttribute("FinishedMatchesPersistenceService", finishedMatchesPersistenceService);
        sce.getServletContext().setAttribute("MatchScoreCalculationService", matchScoreCalculationService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
        sessionFactory.getCurrentSession();
    }
}
