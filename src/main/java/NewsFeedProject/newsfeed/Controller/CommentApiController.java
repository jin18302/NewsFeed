package NewsFeedProject.newsfeed.Controller;


import NewsFeedProject.newsfeed.Dto.CommentAddRequest;
import NewsFeedProject.newsfeed.Dto.CommentResponse;
import NewsFeedProject.newsfeed.Dto.CommentUpdateRequest;
import NewsFeedProject.newsfeed.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentApiController {

    private final CommentService service;

    @PostMapping()
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentAddRequest request){
      CommentResponse response =  service.saveComment(request);

      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

   @PatchMapping ("/{commentid}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable(name = "id") Long id, @RequestBody  CommentUpdateRequest request){
       CommentResponse response = service.updateComment(id, request);

       return ResponseEntity.status(HttpStatus.OK).body(response);
   }

   @DeleteMapping("/{commentid}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name = "id") Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}
