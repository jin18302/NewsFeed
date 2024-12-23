package NewsFeedProject.newsfeed.controller;

import NewsFeedProject.newsfeed.Service.NewsFeedService;
import NewsFeedProject.newsfeed.dto.NewsFeedRequestDto;
import NewsFeedProject.newsfeed.dto.NewsFeedResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<NewsFeedResponseDto>> findAll() {

        List<NewsFeedResponseDto> newsFeedResponseDtoList = newsFeedService.findAll();

        return new ResponseEntity<>(newsFeedResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsFeedResponseDto> findById(@PathVariable Long id) {

        NewsFeedResponseDto newsFeedResponseDto = newsFeedService.findById(id);

        return new ResponseEntity<>(newsFeedResponseDto, HttpStatus.OK);
    }

//    @PatchMapping

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsFeedService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
