package NewsFeedProject.newsfeed.Controller;

import NewsFeedProject.newsfeed.Dto.NewsFeedLikeRequest;
import NewsFeedProject.newsfeed.Dto.NewsFeedLikeResponse;
import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import NewsFeedProject.newsfeed.Service.NewsFeedLikeService;
import NewsFeedProject.newsfeed.filter.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsfeeds/likes")
@RequiredArgsConstructor
public class NewsFeedLikeController {

    private final NewsFeedLikeService newsFeedLikeService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<NewsFeedLikeResponse> saveLike(@RequestBody NewsFeedLikeRequest request){ //Todo requestDto 에 memberid 지울지
       NewsFeedLikeResponse response = newsFeedLikeService.saveLike(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{likeid}")
    public ResponseEntity<Void> deleteLike(@RequestHeader("Authorization") String authorization,
                                           @PathVariable("likeid") Long id){ //Todo

        String token = authorization.substring(7); //Todo 본인 것

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        newsFeedLikeService.deleteLike(id,email);

       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
