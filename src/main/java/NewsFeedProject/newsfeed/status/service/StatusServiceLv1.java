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
    public Void createSingleStatus(StatausRequestDto dto) {

        UserEntity sendUserById = findUserBySendId(dto);
        UserEntity receivceUserById = findUserByReceiveId(dto);

        StatusLv1 newStatusLv1 = StatusLv1.createStatus(sendUserById, receivceUserById);

        statusRepositoryLv1.save(newStatusLv1);

        return null;
    }

    private UserEntity findUserBySendId(StatausRequestDto dto) {
        Optional<UserEntity> UserById = userRepository.findById(dto.getSendid());

        return UserById.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private UserEntity findUserByReceiveId(StatausRequestDto dto) {
        Optional<UserEntity> UserById = userRepository.findById(dto.getReceiveid());

        return UserById.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @Transactional
    public Void deleteStatus(StatausRequestDto dto) {

        UserEntity sendUserById = findUserBySendId(dto);
        UserEntity receivceUserById = findUserByReceiveId(dto);

        Optional<StatusLv1> byReceiveUserId = statusRepositoryLv1.findBySendUserAndReceiveUser(sendUserById,receivceUserById);

        StatusLv1 findStatusLv1 = byReceiveUserId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        statusRepositoryLv1.delete(findStatusLv1);

        return null;
    }
}
