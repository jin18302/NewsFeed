package NewsFeedProject.newsfeed.Dto;

import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import lombok.Getter;


@Getter
public class NewsFeedLikeRequest {
    private String memberId;
    private String newsfeedId;

    public NewsFeedLike toEntity(){
        return new NewsFeedLike(this.memberId, this.newsfeedId);

    }
}
