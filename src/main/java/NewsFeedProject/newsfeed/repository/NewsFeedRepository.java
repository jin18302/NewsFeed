package NewsFeedProject.newsfeed.repository;

import NewsFeedProject.newsfeed.entity.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

    default NewsFeed findByIdOrElseThrow(Long id) {

        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id" + id));
    }
}
