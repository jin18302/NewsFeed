package NewsFeedProject.newsfeed.Dto;


import NewsFeedProject.newsfeed.Entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class CommentResponse {
    private Long id;

    private String content;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public CommentResponse(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedDate();
        this.updateAt = comment.getUpdatedDate();
    }
}
