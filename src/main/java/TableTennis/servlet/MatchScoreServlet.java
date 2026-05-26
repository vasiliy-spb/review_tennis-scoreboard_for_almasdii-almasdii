package TableTennis.servlet;

import TableTennis.dto.MatchScoreModel;
import TableTennis.mapper.MatchScoreMapper;
import TableTennis.model.Match;
import TableTennis.service.OngoingMatchesService;
import TableTennis.utils.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
@Slf4j
public class MatchScoreServlet extends HttpServlet {
    private OngoingMatchesService ongoingMatchesService;
    private final MatchScoreMapper matchScoreMapper = new MatchScoreMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ongoingMatchesService = (OngoingMatchesService) getServletContext()
                .getAttribute("OngoingMatchesService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String param = req.getParameter("uuid");

        Match match = ongoingMatchesService.getById(UUID.fromString(param));
        log.debug("match from cache map : {}",match);

        MatchScoreModel matchScoreModel = matchScoreMapper.mapFrom(match);
        req.setAttribute("match", matchScoreModel);
        req.setAttribute("matchModel",match);
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
