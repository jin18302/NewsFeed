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
@Table(name="comment")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @CreatedDate
    private LocalDateTime creatAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

//    @ManyToOne
//    private NewsFeed newsFeedId;
//
//    @ManyToOne
//    private Member memberId;

    public Comment(String content){
        this.content = content;
    }

    public void update(CommentUpdateRequest request){
        this.content = request.getContent();
    }
}
