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
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            log.debug("Exception handler Filter starts, before doFilter");
            chain.doFilter(request, response);
            log.debug("after doFilter");
        }catch (PlayerSearchValidationException exception){
            httpServletResponse.setStatus(400);
            httpServletRequest.setAttribute("error", exception.getMessage());
            log.debug("Error ",exception);
            httpServletRequest.getRequestDispatcher(JspHelper.getPath("matches")).forward(httpServletRequest, httpServletResponse);
        }
        catch (ValidationException exception) {
            httpServletResponse.setStatus(400);
            httpServletRequest.setAttribute("error", exception.getMessage());
            log.debug("Error ",exception);
            httpServletRequest.getRequestDispatcher(JspHelper.getPath("new-match")).forward(httpServletRequest, httpServletResponse);
        } catch (BadRequestException exception) {
            httpServletResponse.setStatus(400);
            httpServletRequest.setAttribute("error", exception.getMessage());
            log.debug("Error ",exception);
            httpServletRequest.getRequestDispatcher("/error").forward(httpServletRequest, httpServletResponse);
        } catch (MatchNotFoundException exception) {
            httpServletResponse.setStatus(404);
            httpServletRequest.setAttribute("error", exception.getMessage());
            log.debug("Error ",exception);
            httpServletRequest.getRequestDispatcher("/error").forward(httpServletRequest, httpServletResponse);
        } catch (Exception exception) {
            log.error("Exception ", exception);
            httpServletResponse.setStatus(500);
            httpServletRequest.setAttribute("error", exception.getMessage());
            log.debug("Error ",exception);
            httpServletRequest.getRequestDispatcher("/error").forward(httpServletRequest, httpServletResponse);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
