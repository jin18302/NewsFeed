package NewsFeedProject.newsfeed.status.service;

import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
import NewsFeedProject.newsfeed.status.entity.StatusLv2;
import NewsFeedProject.newsfeed.status.entity.StatusValue;
import NewsFeedProject.newsfeed.status.repository.StatusRepositoryLv2;
import NewsFeedProject.newsfeed.user.UserEntity;
import NewsFeedProject.newsfeed.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class StatusServiceLv2 {

    private final StatusRepositoryLv2 statusRepository;
    private final UserRepository userRepository;

    @Transactional
    public Void createSingleStatus(StatausRequestDto dto) {

        UserEntity sendUserById = findUserBySendId(dto);
        UserEntity receivceUserById = findUserByReceiveId(dto);

        StatusLv2 newStatusLv2 = StatusLv2.createStatus(sendUserById, receivceUserById);

        statusRepository.save(newStatusLv2);

        return null;
    }

    /**
     *
     * @param dto
     * @return
     */
    private UserEntity findUserBySendId(StatausRequestDto dto) {
        Optional<UserEntity> UserById = userRepository.findById(dto.getSendid());

        return UserById.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    /**
     *
     * @param dto
     * @return
     */
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

    @Transactional
    public Void deleteStatus(StatausRequestDto dto) {

        UserEntity sendUserById = findUserBySendId(dto);
        UserEntity receivceUserById = findUserByReceiveId(dto);

        Optional<StatusLv2> byReceiveUserId = statusRepository.findBySendUserAndReceiveUser(sendUserById ,receivceUserById);

        StatusLv2 findStatusLv2 = byReceiveUserId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        statusRepository.delete(findStatusLv2);

        return null;
    }

    @Transactional
    public List<StatusResponseDto> findAllStatus(StatausRequestDto dto) {

        List<StatusResponseDto> statusList = new ArrayList<>();

        UserEntity sendUserById = findUserBySendId(dto);

        List<StatusLv2> bySendUser = statusRepository.findBySendUser(sendUserById);

        for(StatusLv2 status : bySendUser) {
            if(status.getStatusvalue().equals(StatusValue.acceptance)) {
                statusList.add(new StatusResponseDto(status.getSendUser(), status.getReceiveUser(), status.getStatusvalue()));
            }
        }

    return statusList;
    }

    @Transactional
    public StatusResponseDto findByEmailStatus(StatausRequestDto dto,Long receiveId) {

        //
        UserEntity sendUserById = findUserBySendId(dto);

        Optional<UserEntity> receivceUserByIdUserById = userRepository.findById(receiveId);

        UserEntity receivceByUserEntity = receivceUserByIdUserById.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //
        Optional<StatusLv2> statusByUserId = statusRepository.findBySendUserAndReceiveUser(sendUserById ,receivceByUserEntity);

        StatusLv2 statusLv2 = statusByUserId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //
        if(statusLv2.getStatusvalue().equals(StatusValue.acceptance)) {
            return new StatusResponseDto(statusLv2.getSendUser(),statusLv2.getReceiveUser(),statusLv2.getStatusvalue());
         } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
