package NewsFeedProject.newsfeed.Repository;

import NewsFeedProject.newsfeed.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public List<Comment> findCommentByNewsFeedId(Long newsFeedId);
}
