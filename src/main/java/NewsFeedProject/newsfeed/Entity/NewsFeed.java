package NewsFeedProject.newsfeed.Entity;

import NewsFeedProject.newsfeed.Dto.NewsFeedRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "newsfeed")
//@EntityListeners(AuditingEntityListener.class)
public class NewsFeed extends BaseEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;


    public NewsFeed() {}

    public NewsFeed(String title, String contents,Member member) {
        this.title = title;
        this.contents = contents;
        this.member = member;
    }

    public void updateNewsFeed(NewsFeedRequestDto requestDto) {
        if (requestDto.getTitle() != null) {
            this.title = requestDto.getTitle();
        }
        if (requestDto.getContents() != null) {
            this.contents = requestDto.getContents();
        }
    }

//    public void setId(Long id) {
//        this.id = id;
//    }
}
