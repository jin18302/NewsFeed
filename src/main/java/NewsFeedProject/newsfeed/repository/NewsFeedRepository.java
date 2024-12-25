package NewsFeedProject.newsfeed.repository;

import NewsFeedProject.newsfeed.dto.NewsFeedPageResponseDto;
import NewsFeedProject.newsfeed.entity.NewsFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

        @Query(
                "SELECT new NewsFeedProject.newsfeed.dto.NewsFeedPageResponseDto(" +
                        "n.id, n.title, n.createdAt, m.nickname) " +
                        "FROM NewsFeed n JOIN n.member m " +
                        "ORDER BY n.createdAt DESC"
        )

        Page<NewsFeedPageResponseDto> findAllWithPagination(Pageable pageable);

}
