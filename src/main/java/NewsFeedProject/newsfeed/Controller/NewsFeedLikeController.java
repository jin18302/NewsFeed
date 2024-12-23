package NewsFeedProject.newsfeed.Controller;

import NewsFeedProject.newsfeed.Dto.NewsFeedLikeRequest;
import NewsFeedProject.newsfeed.Dto.NewsFeedLikeResponse;
import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import NewsFeedProject.newsfeed.Service.NewsFeedLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/newsfeed/like")
@RequiredArgsConstructor
public class NewsFeedLikeController {

    private final NewsFeedLikeService newsFeedLikeService;

    @PostMapping
    public ResponseEntity<NewsFeedLikeResponse> saveLike(@RequestBody NewsFeedLikeRequest request){
       NewsFeedLikeResponse response = newsFeedLikeService.saveLike(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{likeid}")
    public ResponseEntity<Void> deleteLike(@PathVariable(name = "id") Long id){
       newsFeedLikeService.deleteLike(id);

       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
