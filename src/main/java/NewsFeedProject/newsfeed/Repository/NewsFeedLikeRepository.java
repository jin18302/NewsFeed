package NewsFeedProject.newsfeed.Repository;

import NewsFeedProject.newsfeed.Entity.NewsFeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsFeedLikeRepository extends JpaRepository<NewsFeedLike, Long> {
}
