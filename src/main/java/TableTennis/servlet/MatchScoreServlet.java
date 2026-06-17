package TableTennis.servlet;

import TableTennis.Exception.MatchNotFoundException;
import TableTennis.dto.MatchScoreDto;
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

    // Все повторяющиеся или важные строковые литералы лучше выносить в `private static final` константы с понятными именами.
        // Именованная константа делает код более семантически понятным.

    private OngoingMatchesService ongoingMatchesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Для получения объектов из контекста можно использовать "естественные константы" — ClassName.class.getSimpleName() или ClassName.class.getName()
        ongoingMatchesService = (OngoingMatchesService) getServletContext()
                .getAttribute("OngoingMatchesService");
    }

    // Не нужно в try-catch ловить ServletException | IOException и заворачивать их в RuntimeException.
        // Сигнатуры методов doGet и doPost в HttpServlet специально объявлены как throws ServletException, IOException.
        // Это означает, что фреймворк (или сервлет-контейнер, например, Tomcat) ожидает и умеет обрабатывать именно эти типы исключений.
        // Также оборачивание в RuntimeException не добавляет никакой пользы, но добавляет лишний код.
        // Стоит удалить блок try-catch и позволить методу пробрасывать ServletException и IOException, как и предусмотрено его сигнатурой.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String matchUuid = req.getParameter("uuid");

        // Нет обработки исключений при UUID.fromString(), поэтому в случае ошибки клиент получит ошибку 500 (INTERNAL_SERVER_ERROR), хотя это 400 (BAD_REQUEST)
        // Вызов .orElseThrow() является избыточным — метод ongoingMatchesService.getMatchScoreById() уже бросает исключение, если матч не найден.
        MatchScoreDto matchScoreDto = ongoingMatchesService
                .getMatchScoreById(UUID.fromString(matchUuid)).
                orElseThrow(()->new MatchNotFoundException("Match does not exist")
        );
        log.debug("match from cache map : {}", matchScoreDto);

        req.setAttribute("match", matchScoreDto);
        req.setAttribute("uuid", matchUuid);


        try {
            req.getRequestDispatcher(JspHelper.getPath("match-score")).forward(req, resp);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Не нужно в try-catch ловить IOException и заворачивать его в RuntimeException.
        // Сигнатуры методов doGet и doPost в HttpServlet специально объявлены как throws ServletException, IOException.
        // Это означает, что фреймворк (или сервлет-контейнер, например, Tomcat) ожидает и умеет обрабатывать именно эти типы исключений.
        // Также оборачивание в RuntimeException не добавляет никакой пользы, но добавляет лишний код.
        // Стоит удалить блок try-catch и позволить методу пробрасывать ServletException и IOException, как и предусмотрено его сигнатурой.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String playerName = request.getParameter("player_name");

        // Нет обработки исключений при UUID.fromString(), поэтому в случае ошибки клиент получит ошибку 500 (INTERNAL_SERVER_ERROR), хотя это 400 (BAD_REQUEST)
        UUID matchId = UUID.fromString(request.getParameter("uuid"));

        boolean isMatchFinished = ongoingMatchesService.wonPoint(matchId, playerName);

        try {
            if (isMatchFinished) {
                response.sendRedirect(getServletContext().getContextPath() + "/matches");
            } else {
                response.sendRedirect(getServletContext().getContextPath() + "/match-score?uuid=" + matchId);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
