package NewsFeedProject.newsfeed.Repository;

import NewsFeedProject.newsfeed.Entity.StatusLv2;
import NewsFeedProject.newsfeed.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

public interface StatusRepositoryLv2 extends JpaRepository<StatusLv2,Long> {

    Optional<StatusLv2> findBySendUserAndReceiveUser(UserEntity sendUser, UserEntity receiveUser);

    List<StatusLv2> findBySendUser(UserEntity sendUser);

    List<StatusLv2> findByReceiveUser(UserEntity receiveUser);


    default Map<Long,StatusLv2> findByReceiveUserAndSendUser(UserEntity entity) {

       Map<Long,StatusLv2> statusLv2Map =new HashMap<>();

       if(findBySendUser(entity).isEmpty() && findByReceiveUser(entity).isEmpty()) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }
        if(!findBySendUser(entity).isEmpty()) {
            for (StatusLv2 status :findBySendUser(entity) ) {
                statusLv2Map.put(status.getId(),status);
            }
        }
        if(!findByReceiveUser(entity).isEmpty()) {
            for (StatusLv2 status :findByReceiveUser(entity) ) {
                statusLv2Map.put(status.getId(),status); // 고치고 싶은 부분
            }
        }
        return statusLv2Map;
    }
}
