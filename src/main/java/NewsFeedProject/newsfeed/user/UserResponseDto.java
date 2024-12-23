package NewsFeedProject.newsfeed.user;

import lombok.Getter;

@Getter

public class UserResponseDto {

    private String userName;

    private String email;

    public UserResponseDto(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

}
