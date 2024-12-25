package NewsFeedProject.newsfeed.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto createUser(UserRequestDto dto) {

        UserEntity userEntity = UserEntity.crateUser(dto.getUserName(), dto.getEmail(), dto.getPassword());

        userRepository.save(userEntity);

        UserResponseDto userDto = new UserResponseDto(dto.getUserName(), dto.getEmail());

        return userDto;
    }
}
