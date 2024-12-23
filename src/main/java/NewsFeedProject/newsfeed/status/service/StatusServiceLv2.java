package NewsFeedProject.newsfeed.status.service;

import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
import NewsFeedProject.newsfeed.status.entity.StatusLv2;
import NewsFeedProject.newsfeed.status.repository.StatusRepositoryLv2;
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
public class StatusServiceLv2 {

    private StatusRepositoryLv2 statusRepository;
    private UserRepository userRepository;

    @Transactional
    public Void createSingleStatus(StatausRequestDto dto) {

        UserEntity sendUserById = findUserBySendId(dto);
        UserEntity receivceUserById = findUserByReceiveId(dto);

        StatusLv2 newStatusLv2 = StatusLv2.createStatus(sendUserById, receivceUserById);

        statusRepository.save(newStatusLv2);

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
    public StatusResponseDto setPairStatus(StatausRequestDto dto) {

        UserEntity sendUserById = findUserBySendId(dto);
        UserEntity receivceUserById = findUserByReceiveId(dto);


        Optional<StatusLv2> bySendUserAndReceiveUser = statusRepository.findBySendUserAndReceiveUser(sendUserById ,receivceUserById);

        StatusLv2 findStatusLv2 = bySendUserAndReceiveUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        findStatusLv2.setStatusvalue(dto.getStatusvalue());

        return new StatusResponseDto(sendUserById,receivceUserById,findStatusLv2.getStatusvalue());
    }

    public Void deleteStatus(StatausRequestDto dto) {

        UserEntity sendUserById = findUserBySendId(dto);
        UserEntity receivceUserById = findUserByReceiveId(dto);

        Optional<StatusLv2> byReceiveUserId = statusRepository.findBySendUserAndReceiveUser(sendUserById ,receivceUserById);

        StatusLv2 findStatusLv2 = byReceiveUserId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        statusRepository.delete(findStatusLv2);

        return null;
    }
}
