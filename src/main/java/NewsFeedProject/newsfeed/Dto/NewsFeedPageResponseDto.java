package NewsFeedProject.newsfeed.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class NewsFeedPageResponseDto {

    private  Long id;

    private  String title;

    private LocalDateTime createAt;

    private String memberNickname;

}