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
    public Void createSingleStatus(StatausRequestDto dto, String UserOfEmail) {


        Optional<UserEntity> byEmail = userRepository.findByEmail(UserOfEmail);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        UserEntity userEntity = byEmail.get();
        UserEntity receivceUserById = findUserByReceiveId(dto);

        StatusLv2 newStatusLv2 = StatusLv2.createStatus(userEntity, receivceUserById);

        statusRepository.save(newStatusLv2);

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

    @Transactional
    public StatusResponseDto setPairStatus(StatausRequestDto dto, String UserOfEmail) { // 고칠부분

        // 헛갈리니 주위!
        // 이게 recevice 인거임
        Optional<UserEntity> byEmail = userRepository.findByEmail(UserOfEmail);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        UserEntity userEntity = byEmail.get();

        // 이게 send 가 되는거 // requestDto 의 필드명을 바꿔야할듯 id 를 빼오자? 엄청 헛갈림
        UserEntity receivceUserById = findUserByReceiveId(dto);

        Optional<StatusLv2> bySendUserAndReceiveUser = statusRepository.findBySendUserAndReceiveUser(userEntity ,receivceUserById);

        StatusLv2 findStatusLv2 = bySendUserAndReceiveUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        findStatusLv2.setStatusvalue(dto.getStatusvalue());

        return new StatusResponseDto(userEntity,receivceUserById,findStatusLv2.getStatusvalue());
    }

    @Transactional
    public Void deleteStatus(StatausRequestDto dto, String UserOfEmail) {

        Optional<UserEntity> byEmail = userRepository.findByEmail(UserOfEmail);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        UserEntity userEntity = byEmail.get();
        UserEntity receivceUserById = findUserByReceiveId(dto);

        Optional<StatusLv2> byReceiveUserId = statusRepository.findBySendUserAndReceiveUser(userEntity ,receivceUserById);

        StatusLv2 findStatusLv2 = byReceiveUserId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        statusRepository.delete(findStatusLv2);

        return null;
    }

    @Transactional
    public List<StatusResponseDto> findAllStatus(String UserOfEmail) {

        List<StatusResponseDto> statusList = new ArrayList<>();

        Optional<UserEntity> byEmail = userRepository.findByEmail(UserOfEmail);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        UserEntity userEntity = byEmail.get();

        List<StatusLv2> bySendUser = statusRepository.findBySendUser(userEntity);

        for(StatusLv2 status : bySendUser) {
            if(status.getStatusvalue().equals(StatusValue.acceptance)) {
                statusList.add(new StatusResponseDto(status.getSendUser(), status.getReceiveUser(), status.getStatusvalue()));
            }
        }

    return statusList;
    }

    @Transactional
    public StatusResponseDto findByEmailStatus(Long receiveId, String UserOfEmail) {

        //
        Optional<UserEntity> byEmail = userRepository.findByEmail(UserOfEmail);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        UserEntity userEntity = byEmail.get();

        Optional<UserEntity> receivceUserByIdUserById = userRepository.findById(receiveId);

        UserEntity receivceByUserEntity = receivceUserByIdUserById.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //
        Optional<StatusLv2> statusByUserId = statusRepository.findBySendUserAndReceiveUser(userEntity ,receivceByUserEntity);

        StatusLv2 statusLv2 = statusByUserId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //
        if(statusLv2.getStatusvalue().equals(StatusValue.acceptance)) {
            return new StatusResponseDto(statusLv2.getSendUser(),statusLv2.getReceiveUser(),statusLv2.getStatusvalue());
         } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
