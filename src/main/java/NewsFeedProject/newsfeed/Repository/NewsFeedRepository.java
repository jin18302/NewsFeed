package NewsFeedProject.newsfeed.Repository;

import NewsFeedProject.newsfeed.Entity.NewsFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

    //Page<NewsFeed> findAllByOrderByCreatedAtDesc(Pageable pageable);  // TODO 바꾼 부분

    Page<NewsFeed> findAllByOrderByCreatedDateDesc(Pageable pageable);
}
