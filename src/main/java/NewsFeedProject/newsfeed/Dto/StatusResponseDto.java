package NewsFeedProject.newsfeed.Dto;

import NewsFeedProject.newsfeed.Entity.Member;
import NewsFeedProject.newsfeed.Entity.StatusValue;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class StatusResponseDto {

    private Member sendMember;

    private Member receiveMember;

    private StatusValue statusValue;

    public StatusResponseDto(Member receiveMember, StatusValue statusValue) {
        this.receiveMember = receiveMember;
        this.statusValue = statusValue;
    }

    public StatusResponseDto(Member sendMember, Member receiveMember, StatusValue statusValue) {
        this.sendMember = sendMember;
        this.receiveMember = receiveMember;
        this.statusValue = statusValue;
    }

    public StatusResponseDto(Member sendMember, Member receiveMember) {
        this.sendMember = sendMember;
        this.receiveMember = receiveMember;
    }
}

