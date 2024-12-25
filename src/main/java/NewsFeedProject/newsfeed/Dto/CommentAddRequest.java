package NewsFeedProject.newsfeed.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentAddRequest {

    @NotBlank
    @Size(max = 150)
    private String content;

    @NotNull
    private Long memberId;

    @NotNull
    private Long newsfeedId;

}
