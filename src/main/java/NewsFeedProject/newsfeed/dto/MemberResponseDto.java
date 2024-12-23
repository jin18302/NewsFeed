package NewsFeedProject.newsfeed.dto;

import NewsFeedProject.newsfeed.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MemberResponseDto {

    //아이디 이름 이메일 생년월일 닉네임 소개 생성일
    private final Long id;

    private final String name;

    private final String email;

    private final String birthdate;

    private final String nickname;

    private final String comment;

    private final String createdDate;

    public MemberResponseDto(Long id, String name, String email, String birthdate, String nickname, String comment, String createdDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.nickname = nickname;
        this.comment = comment;
        this.createdDate = createdDate;
    }


    public static MemberResponseDto toDto(Member member){

        String createdDate = createdDate(member.getCreatedDate());

        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getBirthdate(),
                member.getNickname(),
                member.getComment(),
                createdDate
        );

    }

    private static String createdDate(LocalDateTime createdDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdDate.format(formatter);
    }


}
