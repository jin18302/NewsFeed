package NewsFeedProject.newsfeed.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberRequestDto { //Todo 바꾼 부분 필드명에 final 을 빼고, 역직렬화를 위해 생성자 생성

    //이름 이메일 패스워드 생년월일 닉네임 소개

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>])[a-zA-Z0-9!@#$%^&*(),.?\":{}|<>]+$",
            message = "영문 대소문자, 숫자, 특수문자를 각각 최소 1글자씩 포함해야 합니다.")
    private String password;

    @NotBlank
    private String birthdate;

    @NotBlank
    private String nickname;


    private String comment;



    public MemberRequestDto(String name, String email, String password, String birthdate, String nickname, String comment) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.nickname = nickname;
        this.comment = comment;
    }
    public MemberRequestDto() { //Todo 생성자 생성
    }


}
