package NewsFeedProject.newsfeed.status.service;

import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.entity.StatusLv1;
import NewsFeedProject.newsfeed.status.repository.StatusRepositoryLv1;
import NewsFeedProject.newsfeed.user.UserEntity;
import NewsFeedProject.newsfeed.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@AllArgsConstructor
public class StatusServiceLv1 {

    private final StatusRepositoryLv1 statusRepositoryLv1;

    private final UserRepository userRepository;

    @Transactional
    public Void createSingleStatus(StatausRequestDto dto, String sendUserOfEmail) {

        Optional<UserEntity> byEmail = userRepository.findByEmail(sendUserOfEmail);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        UserEntity userEntity = byEmail.get();
        UserEntity receivceUserById = findUserByReceiveId(dto);

        StatusLv1 newStatusLv1 = StatusLv1.createStatus(userEntity, receivceUserById);

        statusRepositoryLv1.save(newStatusLv1);

        return null;
    }

    @Transactional
    public Void deleteStatus(StatausRequestDto dto, String sendUserOfEmail) {

        Optional<UserEntity> byEmail = userRepository.findByEmail(sendUserOfEmail);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        UserEntity userEntity = byEmail.get();
        UserEntity receivceUserById = findUserByReceiveId(dto);

        Optional<StatusLv1> byReceiveUserId = statusRepositoryLv1.findBySendUserAndReceiveUser(userEntity,receivceUserById);

        StatusLv1 findStatusLv1 = byReceiveUserId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        statusRepositoryLv1.delete(findStatusLv1);

        return null;
    }

    /**
     *
     * @param dto
     * @return
     */
    private UserEntity findUserByReceiveId(StatausRequestDto dto) {
        Optional<UserEntity> UserById = userRepository.findById(dto.getUserId()); // 논리적으로는 Receive 가 맞지만, 물리적으로는 이렇게도 맞다.

        return UserById.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
