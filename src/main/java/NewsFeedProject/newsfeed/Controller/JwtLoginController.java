package NewsFeedProject.newsfeed.Controller;


import NewsFeedProject.newsfeed.Service.JwtLoginService;
import NewsFeedProject.newsfeed.Dto.JwtRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class JwtLoginController {

    private final JwtLoginService jwtLoginService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody JwtRequestDto dto) {
        log.info("로그인 컨트롤러 1");
        String token = jwtLoginService.login(dto.getEmail(), dto.getPassword());
        log.info("로그인 컨트롤러 2");

        log.info("Generated token: " + token);
        return ResponseEntity.ok()
                .header("Authorization","Bearer "+token).build();
    }
}
