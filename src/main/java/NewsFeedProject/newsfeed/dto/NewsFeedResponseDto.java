package NewsFeedProject.newsfeed.dto;

import NewsFeedProject.newsfeed.entity.NewsFeed;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewsFeedResponseDto {

    private final Long id;

    private final String title;

    private final String contents;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    public NewsFeedResponseDto(Long id, String title, String contents, LocalDateTime createAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public static NewsFeedResponseDto toDto(NewsFeed newsFeed) {
        return new NewsFeedResponseDto(newsFeed.getId(), newsFeed.getTitle(), newsFeed.getContents(), newsFeed.getCreateAt(), newsFeed.getModifiedAt());
    }
}
