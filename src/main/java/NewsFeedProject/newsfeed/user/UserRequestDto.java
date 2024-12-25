package NewsFeedProject.newsfeed.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    private String userName;

    private String email;

    private String password;

    public UserRequestDto() {
    }


}
