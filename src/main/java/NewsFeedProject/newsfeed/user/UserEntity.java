package NewsFeedProject.newsfeed.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@Table(name="newsfeed")
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    @ToString.Exclude // @ToString.Exclude는 Lombok이 생성하는 toString() 메서드에만 영향을 준다!
    @JsonIgnore // json 형태로 직렬화 할 때, 써주는걸?보여주는걸 제외
    @Column(nullable = false)
    private String password;

    private UserEntity(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public UserEntity() {
    }

    public static UserEntity crateUser(String userName, String email, String password) {

        return new UserEntity(userName,email,password);
    }
}
