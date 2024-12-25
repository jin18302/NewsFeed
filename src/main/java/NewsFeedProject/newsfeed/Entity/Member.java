package NewsFeedProject.newsfeed.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "member")
public class Member extends BaseEntity{

    //이름 이메일 패스워드 생년월일 닉네임 소개
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String birthdate;

    private String nickname;

    private String comment;

    public Member(String name, String email, String password, String birthdate, String nickname, String comment) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.nickname = nickname;
        this.comment = comment;
    }

    public Member() {

    }


}
