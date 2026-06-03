package TableTennis.servlet;

import TableTennis.dto.MatchResponse;
import TableTennis.service.FinishedMatchesPersistenceService;
import TableTennis.utils.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.DialectOverride;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    private FinishedMatchesPersistenceService finishedService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.finishedService = (FinishedMatchesPersistenceService) getServletContext().getAttribute("FinishedMatchesPersistenceService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String playerName = req.getParameter("filter_by_player_name");
        String pageNumberParam = req.getParameter("page");

        int pageNumber = 0;
        if(pageNumberParam != null){
            pageNumber = Integer.parseInt(pageNumberParam);
        }

        List<MatchResponse> all =
                finishedService.findAll(playerName,pageNumber);
        int numberOfPages = finishedService.numberOfPages();
        int pageSize = FinishedMatchesPersistenceService.DEFAULT_PAGE_SIZE;
        req.setAttribute("matches",all);
        req.setAttribute("pageSize",pageSize);
        req.setAttribute("pageNumber",pageNumber);
        req.setAttribute("filterName",playerName);
        req.setAttribute("numberOfPages",numberOfPages);
        try {
            req.getRequestDispatcher(JspHelper.getPath("matches")).forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
