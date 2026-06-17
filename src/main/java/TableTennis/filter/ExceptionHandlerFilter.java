package TableTennis.filter;

import TableTennis.Exception.*;
import TableTennis.utils.JspHelper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter("/*")
public class ExceptionHandlerFilter implements Filter {

    // Можно подобрать более информативные сообщения для логирования, а также для возврата клиенту.

    // Вместо захардкоженных чисел в качестве кодов ответа можно использовать готовые константы из HttpServletResponse

    // Стоит подумать, как избавиться от большого количества дублирующихся частей в методе doFilter


    // Метод init можно не переопределять только для того, чтобы вызвать в нём метод родительского класса.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // Можно наследоваться от HttpFilter и переопределять метод 'void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)'
            // с уже приведёнными HttpServletRequest и HttpServletResponse
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            log.debug("Exception handler Filter starts, before doFilter");
            chain.doFilter(request, response);
            log.debug("after doFilter");
        }catch (PlayerSearchValidationException exception){
            httpServletResponse.setStatus(400); // HttpServletResponse.SC_BAD_REQUEST
            httpServletRequest.setAttribute("error", exception.getMessage());
            log.warn("Error ",exception);
            httpServletRequest.getRequestDispatcher(JspHelper.getPath("matches")).forward(httpServletRequest, httpServletResponse);
        }
        catch (ValidationException exception) {
            httpServletResponse.setStatus(400); // HttpServletResponse.SC_BAD_REQUEST
            httpServletRequest.setAttribute("error", exception.getMessage());
            log.warn("Error ",exception);
            httpServletRequest.getRequestDispatcher(JspHelper.getPath("new-match")).forward(httpServletRequest, httpServletResponse);
        } catch (BadRequestException exception) {
            httpServletResponse.setStatus(400); // HttpServletResponse.SC_BAD_REQUEST
            httpServletRequest.setAttribute("error", exception.getMessage());
            log.error("Error ",exception);
            httpServletRequest.getRequestDispatcher("/error").forward(httpServletRequest, httpServletResponse);
        } catch (MatchNotFoundException exception) {
            httpServletResponse.setStatus(404); // HttpServletResponse.SC_NOT_FOUND
            httpServletRequest.setAttribute("error", exception.getMessage());
            log.warn("Error ",exception);
            httpServletRequest.getRequestDispatcher("/error").forward(httpServletRequest, httpServletResponse);
        }catch (DataBaseException exception){
            httpServletResponse.setStatus(500); // HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            httpServletRequest.setAttribute("error", "Error with Db");
            log.error("Error ",exception);
            httpServletRequest.getRequestDispatcher("/error").forward(httpServletRequest, httpServletResponse);
        }
        catch (Exception exception) {
            log.error("Exception ", exception);
            httpServletResponse.setStatus(500); // HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            httpServletRequest.setAttribute("error", "Unexpected Error");
            httpServletRequest.getRequestDispatcher("/error").forward(httpServletRequest, httpServletResponse);
        }
    }

    // Метод destroy можно не переопределять только для того, чтобы вызвать в нём метод родительского класса.
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
