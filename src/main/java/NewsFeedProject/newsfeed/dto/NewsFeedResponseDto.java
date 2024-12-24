package NewsFeedProject.newsfeed.dto;

import NewsFeedProject.newsfeed.entity.NewsFeed;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NewsFeedResponseDto {

    private final Long id;

    private final String title;

    private final String contents;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public static NewsFeedResponseDto toDto(NewsFeed newsFeed) {
        return new NewsFeedResponseDto(newsFeed.getId(),
                newsFeed.getTitle(),
                newsFeed.getContents(),
                newsFeed.getCreatedAt(),
                newsFeed.getModifiedAt()
        );
    }
}
