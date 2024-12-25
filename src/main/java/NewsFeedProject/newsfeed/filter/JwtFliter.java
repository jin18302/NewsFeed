package NewsFeedProject.newsfeed.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
@Component
@AllArgsConstructor
public class JwtFliter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        // 1. 로그인/회원가입 URI 체크 개선
        if (requestURI.startsWith("/login") || requestURI.startsWith("/users")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token = request.getHeader("Authorization");
        log.info("Authorization token : {}", token); // 로그 형식 개선
        // 2. 토큰 검증 로직 개선
        if (token == null || !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization header is missing or invalid.");
        }
        try {
            // 3. 토큰 파싱 부분 개선
            String jwt = token.substring(7); // "Bearer " 이후 부분만 추출
            Claims claims = jwtTokenProvider.validateToken(jwt);
            log.info("Token validated successfully for URI: {}", requestURI);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token: " + e.getMessage());
        }
    }
}

