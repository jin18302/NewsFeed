package NewsFeedProject.newsfeed.Controller;


import NewsFeedProject.newsfeed.Dto.CommentAddRequest;
import NewsFeedProject.newsfeed.Dto.CommentResponse;
import NewsFeedProject.newsfeed.Dto.CommentUpdateRequest;
import NewsFeedProject.newsfeed.Service.CommentService;
import NewsFeedProject.newsfeed.filter.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeeds/comments")
public class CommentController {

    private final CommentService service;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@Valid @RequestBody CommentAddRequest request){
      CommentResponse response =  service.saveComment(request);

      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{newsfeedid}")
    public ResponseEntity<List<CommentResponse>> findByNewsFeedId(@PathVariable(name = "newsfeedid")Long newsFeedId){
        List<CommentResponse> comments = service.findByNewsFeedId(newsFeedId);

        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }


    @PatchMapping ("/{commentid}")
    public ResponseEntity<CommentResponse> updateComment(@RequestHeader("Authorization") String authorization,
                                                         @PathVariable("commentid") Long id, @Valid @RequestBody  CommentUpdateRequest request){ //Todo

       String token = authorization.substring(7); //Todo 본인 것

       Claims claims = jwtTokenProvider.validateToken(token);

       String email = claims.getSubject();

       CommentResponse response = service.updateComment(id, request, email);

       return ResponseEntity.status(HttpStatus.OK).body(response);
   }

   @DeleteMapping("/{commentid}")
    public ResponseEntity<Void> deleteComment(@RequestHeader("Authorization") String authorization,
                                              @PathVariable("commentid") Long id){

       String token = authorization.substring(7); //Todo 본인 것

       Claims claims = jwtTokenProvider.validateToken(token);

       String email = claims.getSubject();

        service.delete(id, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}
