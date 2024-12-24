package NewsFeedProject.newsfeed.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateRequest {

    @NotBlank
    @Size(max = 150)
    private String content;
}
