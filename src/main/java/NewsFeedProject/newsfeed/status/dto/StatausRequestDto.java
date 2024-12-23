package NewsFeedProject.newsfeed.status.dto;


import NewsFeedProject.newsfeed.status.entity.StatusValue;
import lombok.Getter;

@Getter
public class StatausRequestDto {

    private Long userId;

    private Long otherUserId;

    private StatusValue statusvalue;


    public StatausRequestDto(Long userId, Long otherUserId) {
        this.userId = userId;
        this.otherUserId = otherUserId;
    }

    public StatausRequestDto(Long userId) {
        this.userId = userId;
    }

    public StatausRequestDto(Long userId, Long otherUserId, StatusValue statusvalue) {
        this.userId = userId;
        this.otherUserId = otherUserId;
        this.statusvalue = statusvalue;
    }

    public StatausRequestDto() {
    }
}
