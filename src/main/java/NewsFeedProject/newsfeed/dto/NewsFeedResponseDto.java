package NewsFeedProject.newsfeed.dto;

import NewsFeedProject.newsfeed.entity.NewsFeed;
import lombok.Getter;

@Getter
public class NewsFeedResponseDto {

    private final Long id;

    private final String title;

    private final String contents;

    public NewsFeedResponseDto(Long id, String title, String contents) {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    public static NewsFeedResponseDto toDto(NewsFeed newsFeed) {
        return new NewsFeedResponseDto(newsFeed.getId(), newsFeed.getTitle(), newsFeed.getContents());
    }
}
