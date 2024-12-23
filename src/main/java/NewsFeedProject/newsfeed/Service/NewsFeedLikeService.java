package NewsFeedProject.newsfeed.Service;

import NewsFeedProject.newsfeed.Dto.NewsFeedLikeRequest;
import NewsFeedProject.newsfeed.Dto.NewsFeedLikeResponse;
import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import NewsFeedProject.newsfeed.Repository.NewsFeedLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsFeedLikeService {

    private final NewsFeedLikeRepository likeRepository;

    public NewsFeedLikeResponse saveLike(NewsFeedLikeRequest request) {
        NewsFeedLike newsFeedLike = likeRepository.save(request.toEntity());
        return new NewsFeedLikeResponse(newsFeedLike);
    }


    public void deleteLike(Long id) {
        likeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Can't find that like"));
        likeRepository.deleteById(id);
    }
}
