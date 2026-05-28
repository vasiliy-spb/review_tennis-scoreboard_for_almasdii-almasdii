package TableTennis.filter;

import TableTennis.Exception.BadRequestException;
import TableTennis.Exception.MatchNotFoundException;
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
        try{
            log.debug("Exception handler Filter starts, before doFilter");
            chain.doFilter(request,response);
            log.debug("after doFilter");
        }catch (BadRequestException exception){
            log.warn("BadRequestException called ",exception);
//            httpServletResponse.sendError(400,exception.getMessage());
            httpServletResponse.setStatus(400);
            httpServletRequest.setAttribute("error",exception.getMessage());
            httpServletRequest.getRequestDispatcher(JspHelper.getPath("new-match")).forward(httpServletRequest,httpServletResponse);
        }catch (MatchNotFoundException exception){
            log.warn("match not found",exception);
        }
        catch (Exception exception){
            log.error("Exception ",exception);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
