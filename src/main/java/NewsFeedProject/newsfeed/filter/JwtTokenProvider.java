package NewsFeedProject.newsfeed.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


    @Component
    public class JwtTokenProvider {
        private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
                "M/vlcmUUr69ugMudo+GgWCV0tzZgQ3nskHD8UhhIkkE="
                        .getBytes(StandardCharsets.UTF_8)
        );
        private final Long EXPIRATION_TIME = 86400000L;  // 24시간
        public String createToken(String email, String memberName) {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
            return Jwts.builder()
                    .setSubject(email)
                    .claim("name", memberName)
                    .setIssuedAt(now)  // 발급시간
                    .setIssuer("NewsFeedProject")  // 발급자
                    .setExpiration(expiryDate)
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                    .compact();
        }
        public Claims validateToken(String token) {
            return Jwts.parserBuilder()  // parser() 대신 parserBuilder() 사용
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
    }

