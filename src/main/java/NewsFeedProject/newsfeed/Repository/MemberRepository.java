package NewsFeedProject.newsfeed.Repository;

import NewsFeedProject.newsfeed.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일치하는 정보를 찾을 수 없습니다."));
    }

    Optional<Member> findByEmail(String email);

    default Member findByEmailOrElseThrow(String email) { //Todo 사용하는 게 없어서 만듬 , 로그인 햇을 당시 용으로
        return findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일 입니다"));
    }

}
