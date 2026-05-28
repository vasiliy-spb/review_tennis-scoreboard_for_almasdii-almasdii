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
        String playerName = req.getParameter("filter_by_player_name");
        int pageNumber = Integer.parseInt(req.getParameter("page_number"));
        if(playerName != null & !playerName.isEmpty()){
            req.setAttribute("filter_by_player_name",playerName);
        }
        if(pageNumber > 0){
            req.setAttribute("page_number",pageNumber);
        }

        List<MatchResponse> all =
                finishedService.findAll(pageNumber,playerName);


        req.setAttribute("matches",all);
        try {
            req.getRequestDispatcher(JspHelper.getPath("matches")).forward(req,resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
