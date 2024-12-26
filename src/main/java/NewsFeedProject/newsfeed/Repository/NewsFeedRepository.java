package NewsFeedProject.newsfeed.Repository;

import NewsFeedProject.newsfeed.Dto.NewsFeedPageResponseDto;
import NewsFeedProject.newsfeed.Entity.NewsFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

    @Query(
            "SELECT new NewsFeedProject.newsfeed.Dto.NewsFeedPageResponseDto(" +
                    "n.id, n.title, n.createdAt, m.nickname) " +
                    "FROM NewsFeed n JOIN n.member m " +
                    "ORDER BY n.createdAt DESC"
    )

    Page<NewsFeedPageResponseDto> findAllWithPagination(Pageable pageable);
}
