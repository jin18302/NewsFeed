package NewsFeedProject.newsfeed.Controller;


import NewsFeedProject.newsfeed.filter.JwtTokenProvider;
import NewsFeedProject.newsfeed.Dto.StatausRequestDto;
import NewsFeedProject.newsfeed.Dto.StatusResponseDto;
import NewsFeedProject.newsfeed.Service.StatusServiceLv2;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("lv2/statuses")
@AllArgsConstructor
public class StatusControllerLv2 {

    private final StatusServiceLv2 statusService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<Void> createSingleStatus(@RequestHeader("Authorization") String authorization,
                                                   @Valid @RequestBody StatausRequestDto dto) {

        log.info("Authorization token : " + authorization);

        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        return ResponseEntity.ok(statusService.createSingleStatus(dto,email));
    }

    @PatchMapping ("/{sendUserId}")
    public ResponseEntity<StatusResponseDto> setPairStatus(@RequestHeader("Authorization") String authorization,
                                                            @Valid @RequestBody StatausRequestDto dto) {

        log.info("Authorization token : " + authorization);

        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        return ResponseEntity.ok(statusService.setPairStatus(dto,email));
    }

    @GetMapping
    public ResponseEntity<List<StatusResponseDto>> findAllStatus(@RequestHeader("Authorization") String authorization) {

        log.info("Authorization token : " + authorization);

        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        return ResponseEntity.ok(statusService.findAllStatus(email));
    }

    // 수락한 친구 모두 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StatusResponseDto>> findAcceptanceStatus(@RequestHeader("Authorization") String authorization) {

        log.info("Authorization token : " + authorization);

        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        return ResponseEntity.ok(statusService.findAcceptanceStatus(email));
    }

    @GetMapping("/{receiveUserId}")
    public ResponseEntity<StatusResponseDto> findByEmailStatus(@RequestHeader("Authorization") String authorization,
                                                               @Valid @RequestBody StatausRequestDto dto) {

        log.info("Authorization token : " + authorization);

        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        return ResponseEntity.ok(statusService.findByEmailStatus(dto,email));
    }

    @DeleteMapping("/{receiveUserId}")
    public ResponseEntity<Void> deleteStatus(@RequestHeader("Authorization") String authorization,
                                             @Valid @RequestBody StatausRequestDto dto) {

        log.info("Authorization token : " + authorization);

        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        statusService.deleteStatus(dto,email);

        return ResponseEntity.noContent().build();
    }




}
