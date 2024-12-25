package NewsFeedProject.memberbranch.newsfeed.dto;

import lombok.Getter;

@Getter
public class NewsFeedRequestDto {

    private final String title;

    private final String contents;

    public NewsFeedRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
