package NewsFeedProject.newsfeed.status.dto;

import NewsFeedProject.newsfeed.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class StatusResponseDto {

    private UserEntity sendUser;

    private UserEntity receiveUser;
}
