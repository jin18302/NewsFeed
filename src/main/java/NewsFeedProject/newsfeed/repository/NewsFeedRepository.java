package NewsFeedProject.newsfeed.repository;

import NewsFeedProject.newsfeed.entity.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {
}
