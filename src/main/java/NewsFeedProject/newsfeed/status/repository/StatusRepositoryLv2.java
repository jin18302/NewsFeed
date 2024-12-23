package NewsFeedProject.newsfeed.status.repository;

import NewsFeedProject.newsfeed.status.entity.StatusLv2;
import NewsFeedProject.newsfeed.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepositoryLv2 extends JpaRepository<StatusLv2,Long> {

    Optional<StatusLv2> findBySendUserAndReceiveUser(UserEntity sendUser, UserEntity receiveUser);

}
