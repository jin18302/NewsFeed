package NewsFeedProject.newsfeed.Dto;


import NewsFeedProject.newsfeed.Entity.StatusValue;
import NewsFeedProject.newsfeed.EnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import jakarta.validation.constraints.Pattern;


@Getter
public class StatausRequestDto {

    private Long memberId;

    private Long otherMemberId;

    @EnumValid(enumClass = StatusValue.class , message = "enum")
    private StatusValue statusvalue;


    public StatausRequestDto(Long memberId, Long otherMemberId) {
        this.memberId = memberId;
        this.otherMemberId = otherMemberId;
    }

    public StatausRequestDto(StatusValue statusvalue, Long otherMemberId) {
        this.statusvalue = statusvalue;
        this.otherMemberId = otherMemberId;
    }

    public StatausRequestDto(Long memberId) {
        this.memberId = memberId;
    }

    public StatausRequestDto(Long memberId, Long otherMemberId, StatusValue statusvalue) {
        this.memberId = memberId;
        this.otherMemberId = otherMemberId;
        this.statusvalue = statusvalue;
    }

    public StatausRequestDto() {
    }
}
