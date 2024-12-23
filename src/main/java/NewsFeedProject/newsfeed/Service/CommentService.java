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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponse saveComment(CommentAddRequest request) {
        Optional<Member> findMember = memberRepository.findById(request.getMemberId());
        findMember.orElseThrow(NullPointerException::new);
        Member member = findMember.get();

        Optional<NewsFeed> findNewsFeed = newsFeedRepository.findByid(request.getNewsfeedId());
        findNewsFeed.orElseThrow(NullPointerException::new);

        NewsFeed newsFeed = findNewsFeed.get();

        Comment comment = new Comment(request.getContent(), member, newsFeed);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(savedComment);

    }

    public CommentResponse updateComment(Long id, CommentUpdateRequest request) {
        Optional<Comment> findComment = commentRepository.findById(id);
        findComment.orElseThrow(NullPointerException::new);


        Comment comment = findComment.get();
        comment.update(request);

        Comment c = commentRepository.save(comment);

        return new CommentResponse(c);
    }


    public void delete(Long id) {
        Optional<Comment> findComment = commentRepository.findById(id);

        findComment.orElseThrow(NullPointerException::new);

        commentRepository.deleteById(id);
    }
}
