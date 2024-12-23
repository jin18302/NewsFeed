package NewsFeedProject.newsfeed.controller;

import NewsFeedProject.newsfeed.Service.NewsFeedService;
import NewsFeedProject.newsfeed.dto.NewsFeedRequestDto;
import NewsFeedProject.newsfeed.dto.NewsFeedResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/newsfeeds")
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    @PostMapping
    public ResponseEntity<NewsFeedResponseDto> save(@RequestBody NewsFeedRequestDto requestDto) {

        NewsFeedResponseDto newsFeedResponseDto = newsFeedService.save(requestDto.getTitle(), requestDto.getContents());

        return new ResponseEntity<>(newsFeedResponseDto, HttpStatus.CREATED);
    }
}
