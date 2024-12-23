package NewsFeedProject.newsfeed.status.repository;

import NewsFeedProject.newsfeed.status.entity.Status;
import NewsFeedProject.newsfeed.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status,Long> {

    Optional<Status> findBySendUserAndReceiveUser(UserEntity sendUser, UserEntity receiveUser);

}
