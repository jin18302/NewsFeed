package NewsFeedProject.newsfeed.Entity;


import NewsFeedProject.newsfeed.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Entity
@Table(name="status")
@ToString
public class StatusLv2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 하나의 객체에서 두개이상의 연관 관계를 맺고자 할때!
    @JoinColumn(name = "send_user_id")
    // private UserEntity userEntity; < - 이처럼 하면 안되고, 객체 명을 다르게 구분해 주어야한다.
    private UserEntity sendUser;

    @ManyToOne
    @JoinColumn(name = "receive_user_id")
    private UserEntity receiveUser;

    @Enumerated(EnumType.STRING) // ORDINAL 은 enum 의 순서를 저장
    private StatusValue statusvalue;

    public StatusLv2() {
    }

    private StatusLv2(UserEntity sendUser, UserEntity receiveUser) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
        this.statusvalue = StatusValue.stay;
    }

    public static StatusLv2 createStatus(UserEntity sendUser, UserEntity receiveUser) {

        return new StatusLv2(sendUser,receiveUser);
    }

    public void setStatusvalue(StatusValue statusvalue) {
        this.statusvalue = statusvalue;
    }
}
