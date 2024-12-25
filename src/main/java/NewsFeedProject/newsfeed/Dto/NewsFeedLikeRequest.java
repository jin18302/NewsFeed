package NewsFeedProject.newsfeed.Dto;

import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NewsFeedLikeRequest {
    private Long memberId;
    private Long newsfeedId;
}
