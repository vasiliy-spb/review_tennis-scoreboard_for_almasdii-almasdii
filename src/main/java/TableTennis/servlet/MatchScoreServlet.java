package TableTennis.servlet;

import TableTennis.model.MatchScoreModel;
import TableTennis.model.OngoingMatch;
import TableTennis.model.TaiBreak;
import TableTennis.service.OngoingMatchesService;
import TableTennis.utils.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ongoingMatchesService = (OngoingMatchesService) getServletContext()
                .getAttribute("OngoingMatchesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String param = req.getParameter("uuid");

        OngoingMatch match = ongoingMatchesService.getById(UUID.fromString(param));
        MatchScoreModel matchScoreModel = new MatchScoreModel(
                match.getPlayer1().getName()
                , match.getPlayer2().getName()
                , match.getPlayer1Points().getScore()
                , match.getPlayer2Points().getScore()
                , match.getPlayer1Games()
                , match.getPlayer2Games()
                , match.getPlayer1Sets()
                , match.getPlayer2Sets());
        req.setAttribute("match2",match);
        req.setAttribute("match", matchScoreModel);
        req.setAttribute("uuid", param);
        try {
            if(match.isFinished()){
                resp.sendRedirect("matches");
            }else {
                req.getRequestDispatcher(JspHelper.getPath("match-score")).forward(req, resp);
            }
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String playerName = request.getParameter("player_name");
        UUID matchId = UUID.fromString(request.getParameter("uuid"));

        ongoingMatchesService.wonPoint(matchId, playerName);

        try {
            response.sendRedirect("/match-score?uuid=" + matchId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
