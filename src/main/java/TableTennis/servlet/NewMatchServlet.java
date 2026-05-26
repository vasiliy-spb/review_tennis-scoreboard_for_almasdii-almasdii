package TableTennis.servlet;

import TableTennis.dto.MatchRequest;
import TableTennis.service.OngoingMatchesService;
import TableTennis.utils.JspHelper;
import TableTennis.utils.ValidatorUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private OngoingMatchesService service;
    public NewMatchServlet(){

    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.service = (OngoingMatchesService) getServletContext().getAttribute("OngoingMatchesService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstPlayer = request.getParameter("playerOne");
        String secondPlayer = request.getParameter("playerTwo");


        List<String> errors = ValidatorUtil.isNameCorrect(firstPlayer, secondPlayer);

        if(!errors.isEmpty()){
            Optional<String> reduce =
                    errors.stream().reduce((a, b) -> a + "\n" + b);
            response.sendError(400,reduce.get());
            log.debug("Error sent : \n{}",reduce.get());
            return;
        }

        log.debug("first player name : {} , second player name : {}",firstPlayer,secondPlayer);

        MatchRequest matchRequest = new MatchRequest(firstPlayer,secondPlayer);
        UUID uuid = service.createMatch(matchRequest);

        response.sendRedirect("/match-score?uuid="+uuid);
    }
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response){
        try {
            request.getRequestDispatcher(JspHelper.getPath("new-match")).forward(request,response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}