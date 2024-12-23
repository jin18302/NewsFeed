package NewsFeedProject.newsfeed.login;

import NewsFeedProject.newsfeed.user.UserEntity;
import NewsFeedProject.newsfeed.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    // private final LoginRepository loginRepository;
    private final UserRepository userRepository;

    public UserEntity login(LoginRequestDto dto) {
        //
        Optional<UserEntity> byEmail = userRepository.findByEmail(dto.getEmail());
        UserEntity userEntity = byEmail.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //
        if(!userEntity.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } return userEntity;
    }
}
