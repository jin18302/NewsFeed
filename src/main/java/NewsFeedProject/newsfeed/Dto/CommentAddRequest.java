package NewsFeedProject.newsfeed.Dto;

import NewsFeedProject.newsfeed.Entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentAddRequest {
    private String content;

    private Long memberId;

    private Long newsfeedId;

}
