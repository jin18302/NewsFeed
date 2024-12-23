package NewsFeedProject.newsfeed.status.dto;


import NewsFeedProject.newsfeed.status.entity.StatusValue;
import lombok.Getter;

@Getter
public class StatausRequestDto {

    private Long sendid;

    private Long receiveid;

    private StatusValue statusvalue;


    public StatausRequestDto(Long sendid, Long receiveid) {
        this.sendid = sendid;
        this.receiveid = receiveid;
    }

    public StatausRequestDto(Long receiveid) {
        this.receiveid = receiveid;
    }

    public StatausRequestDto(Long sendid, Long receiveid, StatusValue statusvalue) {
        this.sendid = sendid;
        this.receiveid = receiveid;
        this.statusvalue = statusvalue;
    }

    public StatausRequestDto() {
    }
}
