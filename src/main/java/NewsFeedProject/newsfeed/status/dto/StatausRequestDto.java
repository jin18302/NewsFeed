package NewsFeedProject.newsfeed.status.dto;


import NewsFeedProject.newsfeed.status.entity.StatusValue;
import NewsFeedProject.newsfeed.user.UserEntity;
import lombok.Getter;

@Getter
public class StatausRequestDto {

    private UserEntity sendUser;

    private UserEntity receiveUser;

    private StatusValue statusvalue;


    public StatausRequestDto(UserEntity sendUser, UserEntity receiveUser) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
    }

    public StatausRequestDto(UserEntity sendUser, UserEntity receiveUser, StatusValue statusvalue) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
        this.statusvalue = statusvalue;
    }

    public StatausRequestDto() {
    }
}
