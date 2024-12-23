package NewsFeedProject.newsfeed.login;


import NewsFeedProject.newsfeed.user.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping // 추가로 해야하는게 db 연결 // 전부 로그인 로직 넣어야함
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto dto, HttpServletRequest request) {

        UserEntity loginUserEntity = loginService.login(dto);

        HttpSession session = request.getSession(true);

        Map<String,String> data = new HashMap<>();
        data.put("name",loginUserEntity.getUserName());
        data.put("email",loginUserEntity.getEmail());
        session.setAttribute("session",data);

        return ResponseEntity.ok().build();
    }
}
