package NewsFeedProject.newsfeed.status.dto;


import NewsFeedProject.newsfeed.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatausRequestDto {

    private UserEntity sendUser;

    private UserEntity receiveUser;

    public StatausRequestDto() {
    }
}
