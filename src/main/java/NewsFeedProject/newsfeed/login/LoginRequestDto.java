package NewsFeedProject.newsfeed.login;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    private String email;

    private String password;

    public LoginRequestDto() {
    }
}
