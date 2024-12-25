package NewsFeedProject.newsfeed.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "newfeedlikes")
@NoArgsConstructor
public class NewsFeedLike extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    @JoinColumn(name = "newsfeed_id")
    @ManyToOne
    private NewsFeed newsFeed;

    public NewsFeedLike(Member member, NewsFeed newsFeed){
        this.member = member;
        this.newsFeed = newsFeed;
    }
}
