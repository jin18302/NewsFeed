package NewsFeedProject.newsfeed.Controller;

import NewsFeedProject.newsfeed.Service.NewsFeedService;
import NewsFeedProject.newsfeed.Dto.NewsFeedRequestDto;
import NewsFeedProject.newsfeed.Dto.NewsFeedResponseDto;
import NewsFeedProject.newsfeed.Entity.NewsFeed;
import NewsFeedProject.newsfeed.filter.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
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
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<NewsFeedResponseDto> createNewsFeed(@RequestHeader("Authorization") String authorization,
                                                              @Valid @RequestBody NewsFeedRequestDto requestDto) {


        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();


        NewsFeedResponseDto newsFeedResponseDto = newsFeedService.create(requestDto.getTitle(), requestDto.getContents(),email);

        return new ResponseEntity<>(newsFeedResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<NewsFeed>> getNewsFeed( // Todo 이건 몰라서 생략
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "1") int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return new ResponseEntity<>(newsFeedService.getNewsFeed(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsFeedResponseDto> getNewsFeedById(@PathVariable("id") Long id) { //Todo


        return new ResponseEntity<>(newsFeedService.getNewsFeedById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NewsFeedResponseDto> updateNewsFeed(@RequestHeader("Authorization") String authorization,
                                                              @PathVariable("id") Long id, @Valid @RequestBody NewsFeedRequestDto requestDto) {

        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        return new ResponseEntity<>(newsFeedService.update(id, requestDto,email), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@RequestHeader("Authorization") String authorization,
                                      @PathVariable("id") Long id) {

        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        newsFeedService.delete(id,email);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
