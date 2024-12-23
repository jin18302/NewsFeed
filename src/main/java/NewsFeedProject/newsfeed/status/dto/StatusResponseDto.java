package NewsFeedProject.newsfeed.status.dto;

import NewsFeedProject.newsfeed.status.entity.StatusValue;
import NewsFeedProject.newsfeed.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class StatusResponseDto {

    private UserEntity sendUser;

    private UserEntity receiveUser;

    private StatusValue statusvalue;

    public StatusResponseDto(UserEntity sendUser, UserEntity receiveUser) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
    }
}
