package TableTennis.servlet;

import TableTennis.dto.MatchRequest;
import TableTennis.service.NewMatchService;
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
        if(firstPlayer == null || secondPlayer == null){
            System.out.println("they Are null");
        }
        System.out.println(firstPlayer);
        System.out.println(secondPlayer);
        // if ok

        MatchRequest request1 = new MatchRequest(firstPlayer,secondPlayer);
        UUID uuid = service.save(request1);

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