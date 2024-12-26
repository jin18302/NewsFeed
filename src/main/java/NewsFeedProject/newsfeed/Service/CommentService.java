package NewsFeedProject.newsfeed.Service;

import NewsFeedProject.newsfeed.Dto.CommentAddRequest;
import NewsFeedProject.newsfeed.Dto.CommentResponse;
import NewsFeedProject.newsfeed.Dto.CommentUpdateRequest;
import NewsFeedProject.newsfeed.Entity.Comment;
import NewsFeedProject.newsfeed.Entity.Member;
import NewsFeedProject.newsfeed.Entity.NewsFeed;
import NewsFeedProject.newsfeed.Repository.CommentRepository;
import NewsFeedProject.newsfeed.Repository.MemberRepository;
import NewsFeedProject.newsfeed.Repository.NewsFeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final NewsFeedRepository newsFeedRepository;

    public CommentResponse saveComment(CommentAddRequest request) {

        Optional<Member> findMember = memberRepository.findById(request.getMemberId());
        findMember.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The member was not found"));
        Member member = findMember.get();

        Optional<NewsFeed> findNewsFeed = newsFeedRepository.findById(request.getNewsfeedId());
        findNewsFeed.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This post was not found"));

        NewsFeed newsFeed = findNewsFeed.get();

        Comment comment = new Comment(request.getContent(), member, newsFeed);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(savedComment);

    }

    public CommentResponse updateComment(Long id, CommentUpdateRequest request, String email) {

        Member member = memberRepository.findByEmail(email).get();//Todo 로그인 된거라 orelsethrow 생략

        Optional<Comment> findComment = commentRepository.findById(id);
        findComment.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such comment found"));
        Comment comment = findComment.get();

        if(comment.getMember()==member) {

            comment.update(request);

            Comment savedComment = commentRepository.save(comment);

            return new CommentResponse(savedComment);

        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"사용자 권한이 없습니다");
        }
    }

    public List<CommentResponse> findByNewsFeedId(Long newsFeedId) {
        List<Comment> findComments = commentRepository.findCommentByNewsFeedId(newsFeedId);

        List<CommentResponse> comments = findComments.stream().map(CommentResponse::new).toList();

        return comments;
    }



    public void delete(Long id, String email) {


        Member member = memberRepository.findByEmail(email).get();//Todo 로그인 된거라 orelsethrow 생략

        Optional<Comment> findComment = commentRepository.findById(id);

        findComment.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such comment found"));

        if(findComment.get().getMember()==member) {

            commentRepository.deleteById(id);

        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"사용자 권한이 없습니다");
        }
    }
}
