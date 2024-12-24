package NewsFeedProject.newsfeed.Service;

import NewsFeedProject.newsfeed.Dto.NewsFeedLikeRequest;
import NewsFeedProject.newsfeed.Dto.NewsFeedLikeResponse;
import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import NewsFeedProject.newsfeed.Repository.NewsFeedLikeRepository;
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

    public NewsFeedLikeResponse saveLike(NewsFeedLikeRequest request) {
        Optional<NewsFeed> findNewsFeed = newsFeedRepository.findById(request.getNewsfeedId());

        findNewsFeed.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "This post was not found"));

        NewsFeed newsFeed = findNewsFeed.get();


        Optional<Member> findMember = memberRepository.findById(request.getMemberId());

        findMember.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The member was not found"));

        Member member = findMember.get();

        Optional<NewsFeedLike> liked = newsFeedLikeRepository.findByMemberAndNewsFeed(member, newsFeed);

        if (!liked.isEmpty()) {
            //이미 좋아요룰 누른상태라면 예외를 발생시킬지, 취소처리할지
        }

        NewsFeedLike like = new NewsFeedLike(member, newsFeed);

        NewsFeedLike savedLike = newsFeedLikeRepository.save(like);

        return new NewsFeedLikeResponse(savedLike);
    }


    public void deleteLike(Long id) {
        newsFeedLikeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can't find that like"));

        newsFeedLikeRepository.deleteById(id);
    }
}
