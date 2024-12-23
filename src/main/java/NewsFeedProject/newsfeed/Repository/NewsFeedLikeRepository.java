package NewsFeedProject.newsfeed.Repository;

import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsFeedLikeRepository extends JpaRepository<NewsFeedLike, Long> {
    public Optional<NewsFeedLike> findByMemberAndNewsFeed(Long memberId, Long newsFeedId);
}
