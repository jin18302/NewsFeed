package NewsFeedProject.newsfeed.memberbranch.dto;

import NewsFeedProject.newsfeed.memberbranch.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MemberUpdateResponseDto {

    private final String name;

    private final String email;

    private final String nickname;

    private final String comment;

    private final String updatedDate;

    public MemberUpdateResponseDto(String name, String email, String nickname, String comment, String updatedDate) {
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.comment = comment;
        this.updatedDate = updatedDate;
    }

    public static MemberUpdateResponseDto updateDto(Member member){

        String updatedDate = updatedDate(member.getUpdatedDate());

        return new MemberUpdateResponseDto(
                member.getName(),
                member.getEmail(),
                member.getNickname(),
                member.getComment(),
                updatedDate

        );

    }

    private static String updatedDate(LocalDateTime updatedDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return updatedDate.format(formatter);
    }


}
