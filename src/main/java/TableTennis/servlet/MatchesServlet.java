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
        List<MatchResponse> all =
                finishedService.findAll();
        req.setAttribute("matches",all);
        try {
            req.getRequestDispatcher(JspHelper.getPath("matches")).forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
