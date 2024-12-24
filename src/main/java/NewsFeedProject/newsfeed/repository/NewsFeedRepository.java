package NewsFeedProject.newsfeed.repository;

import NewsFeedProject.newsfeed.entity.NewsFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

    Page<NewsFeed> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
