package NewsFeedProject.newsfeed.dto;

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
}
