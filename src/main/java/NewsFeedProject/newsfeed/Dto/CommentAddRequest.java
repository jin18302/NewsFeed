package NewsFeedProject.newsfeed.Dto;

import NewsFeedProject.newsfeed.Entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentAddRequest {
    private String content;

    public Comment toEntity(){
       return new Comment(content);
    }
}
