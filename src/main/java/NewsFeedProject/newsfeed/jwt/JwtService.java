package NewsFeedProject.newsfeed.jwt;


import NewsFeedProject.newsfeed.user.UserEntity;
import NewsFeedProject.newsfeed.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class JwtService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(String email, String password) {

        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!userEntity.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return jwtTokenProvider.createToken(userEntity.getEmail(), userEntity.getUserName());
    }

}
