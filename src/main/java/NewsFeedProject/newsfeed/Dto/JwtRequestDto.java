package NewsFeedProject.newsfeed.Dto;


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