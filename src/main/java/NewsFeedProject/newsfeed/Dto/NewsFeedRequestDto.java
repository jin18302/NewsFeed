package NewsFeedProject.newsfeed.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter

public class NewsFeedRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String contents;

    public NewsFeedRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public NewsFeedRequestDto() {//Todo
    }
}
