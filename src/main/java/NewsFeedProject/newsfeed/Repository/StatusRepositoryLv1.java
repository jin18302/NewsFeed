package NewsFeedProject.newsfeed.Repository;

import NewsFeedProject.newsfeed.status.entity.StatusLv1;
import NewsFeedProject.newsfeed.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepositoryLv1 extends JpaRepository<StatusLv1,Long> {

    Optional<StatusLv1> findBySendUserAndReceiveUser(UserEntity sendUser, UserEntity receiveUser);

}
