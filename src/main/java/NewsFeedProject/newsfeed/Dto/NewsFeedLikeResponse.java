package NewsFeedProject.newsfeed.Dto;

import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import lombok.Getter;
import lombok.Setter;

@Getter
public class NewsFeedLikeResponse {
    private final Long id;

    public NewsFeedLikeResponse(NewsFeedLike like){
        this.id = like.getId();
    }
}
