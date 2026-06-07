package TableTennis.servlet;

import TableTennis.Exception.BadRequestException;
import TableTennis.dto.MatchRequest;
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
        String firstPlayerName = request.getParameter("playerOne");
        String secondPlayerName = request.getParameter("playerTwo");


        if(firstPlayerName == null || secondPlayerName == null){
            throw new BadRequestException("names are null");
        }
        firstPlayerName = firstPlayerName.trim();
        secondPlayerName = secondPlayerName.trim();
        request.setAttribute("firstPlayerName",firstPlayerName);
        request.setAttribute("secondPlayerName",secondPlayerName);

        log.info("first player name : {} , second player name : {}",firstPlayerName,secondPlayerName);

        MatchRequest matchRequest = new MatchRequest(firstPlayerName,secondPlayerName);
        UUID uuid = service.createMatch(matchRequest);

        response.sendRedirect(getServletContext().getContextPath() + "/match-score?uuid="+uuid);
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