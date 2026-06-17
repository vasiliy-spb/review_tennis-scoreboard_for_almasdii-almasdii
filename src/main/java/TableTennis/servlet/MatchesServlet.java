package TableTennis.servlet;

import TableTennis.dto.MatchResponse;
import TableTennis.dto.PaginationDto;
import TableTennis.service.FinishedMatchesPersistenceService;
import TableTennis.utils.JspHelper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    // Все повторяющиеся или важные строковые литералы лучше выносить в `private static final` константы с понятными именами.
        // Именованная константа делает код более семантически понятным.

    private FinishedMatchesPersistenceService finishedService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Для получения объектов из контекста можно использовать "естественные константы" — ClassName.class.getSimpleName() или ClassName.class.getName()
        this.finishedService = (FinishedMatchesPersistenceService) getServletContext().getAttribute("FinishedMatchesPersistenceService");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String playerName = req.getParameter("filter_by_player_name");
        String pageNumberParam = req.getParameter("page");

        int pageNumber = 0;
        if(pageNumberParam != null){

            // Нет обработки исключений при Integer.parseInt(), поэтому в случае ошибки клиент получит ошибку 500 (INTERNAL_SERVER_ERROR), хотя это 400 (BAD_REQUEST)
            pageNumber = Integer.parseInt(pageNumberParam);
        }
        log.debug("pageNumber : {} ",pageNumber);

        PaginationDto paginationDto = finishedService.findAll(playerName, pageNumber);
        log.debug("number of pages : {}",paginationDto.numberOfPages());

        req.setAttribute("paginationDto",paginationDto);
        req.setAttribute("filterName",playerName);
        req.getRequestDispatcher(JspHelper.getPath("matches")).forward(req,resp);
    }
}
