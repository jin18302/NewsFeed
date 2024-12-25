package NewsFeedProject.newsfeed.Entity;

import NewsFeedProject.newsfeed.Dto.CommentUpdateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="newsfeedcomment")
@NoArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "newsfeed_id")
    private NewsFeed newsFeed;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Comment( String content, Member member, NewsFeed newsFeed){
        this.content = content;
        this.member = member;
        this.newsFeed = newsFeed;
    }

    public void update(CommentUpdateRequest request){
        this.content = request.getContent();
    }
}
