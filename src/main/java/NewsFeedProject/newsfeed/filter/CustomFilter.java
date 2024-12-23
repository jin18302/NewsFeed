package NewsFeedProject.newsfeed.filter;



import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;



@Slf4j
public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        log.info("로그인 필요 여부 확인");
        log.info("uri 확인 :{}",requestURI);
        // 제외하는 법, 인증 로직 없이 그냥 통과 되게끔 // uri 리소스를 고유하게 식별하는 경로, 일반적으로 도메인 이후의 세부 경로
        if(requestURI.equals("/login") || requestURI.equals("/users")) {
            log.info("로그인 필요 x");
            filterChain.doFilter(request,response);
            return;
        }

        log.info("로그인 필요 o");

        HttpSession session = httpServletRequest.getSession(false);

        log.info("세션 확인");
            if(session == null || session.getAttribute("session")==null) {
                log.info("세션 불일치");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        log.info("유저 확인 완");
            filterChain.doFilter(request,response);
        }
}
