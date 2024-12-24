package NewsFeedProject.newsfeed.Service;

import NewsFeedProject.newsfeed.Dto.NewsFeedLikeRequest;
import NewsFeedProject.newsfeed.Dto.NewsFeedLikeResponse;
import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import NewsFeedProject.newsfeed.Repository.NewsFeedLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class NewsFeedLikeService {
    private final NewsFeedLikeRepository newsFeedLikeRepository;
    private final NewsFeedRepository newsFeedRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public NewsFeedLikeResponse saveLike(NewsFeedLikeRequest request) {
        Optional<NewsFeed> findNewsFeed = newsFeedRepository.findById(request.getNewsfeedId());

        findNewsFeed.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글은 존재하지 않습니다"));

        NewsFeed newsFeed = findNewsFeed.get();


        Optional<Member> findMember = memberRepository.findById(request.getMemberId());

        findMember.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 멤버는 존재하지 않습니다"));

        Member member = findMember.get();

        Optional<NewsFeedLike> liked = newsFeedLikeRepository.findByMemberAndNewsFeed(member, newsFeed);

        if (!liked.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"좋아요는 1번만 가능합니다");
        }

        NewsFeedLike like = new NewsFeedLike(member, newsFeed);

        NewsFeedLike savedLike = newsFeedLikeRepository.save(like);

        return new NewsFeedLikeResponse(savedLike);
    }


    @Transactional
    public void deleteLike(Long id) {
        newsFeedLikeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 좋아요가 존재하지 않습니다"));

        newsFeedLikeRepository.deleteById(id);
    }
}
