package TableTennis.servlet;

import TableTennis.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/tennis")
public class HomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try {
            req.getRequestDispatcher(JspHelper.getPath("index")).forward(req,resp);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
