package NewsFeedProject.newsfeed.controller;

import NewsFeedProject.newsfeed.Service.NewsFeedService;
import NewsFeedProject.newsfeed.dto.NewsFeedPageResponseDto;
import NewsFeedProject.newsfeed.dto.NewsFeedRequestDto;
import NewsFeedProject.newsfeed.dto.NewsFeedResponseDto;
import NewsFeedProject.newsfeed.entity.NewsFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.util.List;

@RestController
@RequestMapping("/newsfeeds")
@RequiredArgsConstructor
public class NewsFeedController {

    private final NewsFeedService newsFeedService;

    @PostMapping
    public ResponseEntity<NewsFeedResponseDto> createNewsFeed(@RequestBody NewsFeedRequestDto requestDto) {

        NewsFeedResponseDto newsFeedResponseDto = newsFeedService.create(requestDto.getTitle(), requestDto.getContents());

        return new ResponseEntity<>(newsFeedResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public  ResponseEntity<Page<NewsFeedPageResponseDto>> getPaginatedPosts(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int pageNumber)
    {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        Page<NewsFeedPageResponseDto> newsfeeds = newsFeedService.getPaginatedNewsFeeds(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(newsfeeds);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsFeedResponseDto> getNewsFeedById(@PathVariable Long id) {
        return new ResponseEntity<>(newsFeedService.getNewsFeedById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NewsFeedResponseDto> updateNewsFeed(@PathVariable Long id, @RequestBody NewsFeedRequestDto requestDto) {

        return new ResponseEntity<>(newsFeedService.update(id, requestDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsFeedService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
