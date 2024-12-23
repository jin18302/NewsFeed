package NewsFeedProject.newsfeed.Dto;

import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import lombok.Getter;


@Getter
public class NewsFeedLikeRequest {
    private Long memberId;
    private Long newsfeedId;

    public NewsFeedLike toEntity(){
        return new NewsFeedLike(this.memberId, this.newsfeedId);

    }
}
