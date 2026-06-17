package TableTennis.servlet;

import TableTennis.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("") // Сервлету было бы уместно обрабатывать путь `/index`, но текущий маппинг этого не отражает.
        // Можно зарегистрировать сервлет сразу на несколько подходящих путей: @WebServlet(urlPatterns = {"", "/index"})
public class BaseServlet extends HttpServlet {

    // Класс называется `BaseServlet` — обычно с префиксом 'Base*' называют общего предка других классов,
        // но здесь это не так. Этот сервлет связан с JSP-страницей `index.jsp`, поэтому можно назвать его IndexServlet.

    // Все повторяющиеся или важные строковые литералы лучше выносить в `private static final` константы с понятными именами.
        // Именованная константа делает код более семантически понятным.

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("index")).forward(req,resp);
    }
}
