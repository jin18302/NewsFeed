package NewsFeedProject.newsfeed.Service;

import NewsFeedProject.newsfeed.Dto.CommentAddRequest;
import NewsFeedProject.newsfeed.Dto.CommentResponse;
import NewsFeedProject.newsfeed.Dto.CommentUpdateRequest;
import NewsFeedProject.newsfeed.Entity.Comment;
import NewsFeedProject.newsfeed.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    public CommentResponse saveComment(CommentAddRequest request){
       Comment comment = repository.save(request.toEntity());
       return new CommentResponse(comment);
    }

    public CommentResponse updateComment(Long id,CommentUpdateRequest request){
       Optional<Comment> findComment = repository.findById(id);// id로 찾은 객체

       if(findComment.isEmpty()){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such comment found");
       }
       Comment comment = findComment.get();//비어있지 않으면 꺼낸다
       comment.update(request);

      Comment c = repository.save(comment);

        return new CommentResponse(c);
    }


    public void delete(Long id){
        Optional<Comment> findComment = repository.findById(id);

        if(findComment.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such comment found");
        }

        repository.deleteById(id);
    }
}
