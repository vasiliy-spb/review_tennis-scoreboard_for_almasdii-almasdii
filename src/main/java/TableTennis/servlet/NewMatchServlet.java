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

    // Все повторяющиеся или важные строковые литералы лучше выносить в `private static final` константы с понятными именами.
        // Именованная константа делает код более семантически понятным.

    private OngoingMatchesService service;

    // Пустой публичный конструктор не нужно объявлять явно
    public NewMatchServlet(){

    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // Для получения объектов из контекста можно использовать "естественные константы" — ClassName.class.getSimpleName() или ClassName.class.getName()
        this.service = (OngoingMatchesService) getServletContext().getAttribute("OngoingMatchesService");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstPlayerName = request.getParameter("playerOne");
        String secondPlayerName = request.getParameter("playerTwo");

        // Логика валидации может быть в валидаторе (и уже находится там, только надо его вызывать из сервлета)
        if(firstPlayerName == null || secondPlayerName == null){

            // Исключения предназначены для обработки исключительных, непредвиденных ситуаций.
                // Неудачная валидация пользовательского ввода — это ожидаемый, штатный сценарий работы приложения.
                // Использование исключений для таких случаев семантически неверно.
                // Сервлет может сам возвращать нужный в таком случае ответ.
            throw new BadRequestException("names are null"); // Сообщение говорит, что оба имени null, хотя проверка в if проверяет ОДНО ИЗ имён null
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

    // Метод `doGet` можно расположить выше `doPost` — по аналогии с родительским классом HttpServlet.
    // Не нужно в try-catch ловить ServletException | IOException и заворачивать их в RuntimeException.
        // Сигнатуры методов doGet и doPost в HttpServlet специально объявлены как throws ServletException, IOException.
        // Это означает, что фреймворк (или сервлет-контейнер, например, Tomcat) ожидает и умеет обрабатывать именно эти типы исключений.
        // Также оборачивание в RuntimeException не добавляет никакой пользы, но добавляет лишний код.
        // Стоит удалить блок try-catch и позволить методу пробрасывать ServletException и IOException, как и предусмотрено его сигнатурой.
    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response){
        try {
            request.getRequestDispatcher(JspHelper.getPath("new-match")).forward(request,response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}