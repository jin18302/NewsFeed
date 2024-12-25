package NewsFeedProject.newsfeed.Repository;

import NewsFeedProject.newsfeed.Entity.Member;
import NewsFeedProject.newsfeed.Entity.StatusLv2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

public interface StatusRepositoryLv2 extends JpaRepository<StatusLv2,Long> {

    Optional<StatusLv2> findBySendMemberAndReceiveMember(Member sendMember, Member receiveMember);

    List<StatusLv2> findBySendMember(Member sendMember);

    List<StatusLv2> findByReceiveMember(Member receiveMember);


    default Map<Long,StatusLv2> findByReceiveMemberAndSendMember(Member entity) {

       Map<Long,StatusLv2> statusLv2Map =new HashMap<>();

       if(findBySendMember(entity).isEmpty() && findByReceiveMember(entity).isEmpty()) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }
        if(!findBySendMember(entity).isEmpty()) {
            for (StatusLv2 status :findBySendMember(entity) ) {
                statusLv2Map.put(status.getId(),status);
            }
        }
        if(!findByReceiveMember(entity).isEmpty()) {
            for (StatusLv2 status :findByReceiveMember(entity) ) {
                statusLv2Map.put(status.getId(),status); // 고치고 싶은 부분
            }
        }
        return statusLv2Map;
    }
}
