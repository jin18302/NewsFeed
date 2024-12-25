package NewsFeedProject.newsfeed.status.dto;

import NewsFeedProject.newsfeed.status.entity.StatusValue;
import NewsFeedProject.newsfeed.user.UserEntity;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class StatusResponseDto {

    private UserEntity sendUser;

    private UserEntity receiveUser;

    private StatusValue statusValue;

    public StatusResponseDto(UserEntity sendUser, UserEntity receiveUser, StatusValue statusValue) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
        this.statusValue = statusValue;
    }

    public StatusResponseDto(UserEntity sendUser, UserEntity receiveUser) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
    }
}

