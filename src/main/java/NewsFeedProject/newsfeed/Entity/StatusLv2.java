package NewsFeedProject.newsfeed.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Entity
@Table(name="memberstatus")
@ToString
public class StatusLv2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 하나의 객체에서 두개이상의 연관 관계를 맺고자 할때!
    @JoinColumn(name = "send_member_id")
    // private UserEntity userEntity; < - 이처럼 하면 안되고, 객체 명을 다르게 구분해 주어야한다.
    private Member sendMember;

    @ManyToOne
    @JoinColumn(name = "receive_member_id")
    private Member receiveMember;

    @Enumerated(EnumType.STRING) // ORDINAL 은 enum 의 순서를 저장
    private StatusValue statusvalue;

    public StatusLv2() {
    }

    private StatusLv2(Member sendMember, Member receiveMember) {
        this.sendMember = sendMember;
        this.receiveMember = receiveMember;
        this.statusvalue = StatusValue.stay;
    }

    public static StatusLv2 createStatus(Member sendMember, Member receiveMember) {

        return new StatusLv2(sendMember,receiveMember);
    }

    public void setStatusvalue(StatusValue statusvalue) {
        this.statusvalue = statusvalue;
    }
}
