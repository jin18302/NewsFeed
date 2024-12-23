package NewsFeedProject.newsfeed.status.entity;


import NewsFeedProject.newsfeed.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Entity
@Table(name="status")
public class StatusLv1 {

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

    public StatusLv1() {
    }

    private StatusLv1(UserEntity sendUser, UserEntity receiveUser) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
    }

    public static StatusLv1 createStatus(UserEntity sendUser, UserEntity receiveUser) {

        return new StatusLv1(sendUser,receiveUser);
    }
}
