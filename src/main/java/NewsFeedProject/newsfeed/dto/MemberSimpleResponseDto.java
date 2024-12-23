package NewsFeedProject.newsfeed.dto;

import NewsFeedProject.newsfeed.entity.Member;
import lombok.Getter;

@Getter
public class MemberSimpleResponseDto {

    private final String nickname;

    private final String comment;

    public MemberSimpleResponseDto(String nickname, String comment) {
        this.nickname = nickname;
        this.comment = comment;
    }

    public static MemberSimpleResponseDto summaryDto(Member member){
        return new MemberSimpleResponseDto(
                member.getNickname(),
                member.getComment()
        );
    }
}
