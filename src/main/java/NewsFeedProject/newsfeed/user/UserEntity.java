package NewsFeedProject.newsfeed.user;


import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name="newsfeed")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

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
