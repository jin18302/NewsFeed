package NewsFeedProject.newsfeed.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtRequestDto {

    private String email;

    private String password;

    public JwtRequestDto() {
    }
}
