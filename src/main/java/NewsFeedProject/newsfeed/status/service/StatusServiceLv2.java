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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Slf4j
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

        // 중복 방지
        if(!statusRepository.findBySendUserAndReceiveUser(receivceUserById ,userEntity).isEmpty()  ) { // && !statusRepository.findBySendUserAndReceiveUser(userEntity ,receivceUserById).isEmpty()
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"이미 상대방이 팔로우 한 상태입니다. 요청을 수락 혹은 거절 하세요"); // 전제 조건 실패의 상태메세지
        }

        StatusLv2 newStatusLv2 = StatusLv2.createStatus(userEntity, receivceUserById);

        log.info("db data : ",newStatusLv2.toString());

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
        log.info("receive user : {}",userEntity);

        // 이게 send 가 되는거 // requestDto 의 필드명을 바꿔야할듯 id 를 빼오자? 엄청 헛갈림
        UserEntity receivceUserById = findUserByReceiveId(dto);
        log.info("send user : {}",receivceUserById);


        //
        if(statusRepository.findBySendUserAndReceiveUser(userEntity ,receivceUserById).isEmpty()) {
            StatusLv2 statusLv2 = statusRepository.findBySendUserAndReceiveUser(receivceUserById, userEntity)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            statusLv2.setStatusvalue(dto.getStatusvalue());


            return new StatusResponseDto(statusLv2.getReceiveUser(),statusLv2.getSendUser(),statusLv2.getStatusvalue());
        }

        StatusLv2 statusLv2 = statusRepository.findBySendUserAndReceiveUser(userEntity, receivceUserById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        statusLv2.setStatusvalue(dto.getStatusvalue());

        return new StatusResponseDto(statusLv2.getSendUser(),statusLv2.getReceiveUser(),statusLv2.getStatusvalue());
    }

    @Transactional
    public Void deleteStatus(StatausRequestDto dto, String UserOfEmail) {

        Optional<UserEntity> byEmail = userRepository.findByEmail(UserOfEmail);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        UserEntity userEntity = byEmail.get();
        UserEntity receivceUserById = findUserByReceiveId(dto);

        // 관계를 양방향 확인 - 데이블에 저장된 데이터는 한쪽 기준으로 검색이 되므로 양쪽으로 다 확인 필요
        if(!statusRepository.findBySendUserAndReceiveUser(userEntity, receivceUserById).isEmpty() || !statusRepository.findBySendUserAndReceiveUser(receivceUserById ,userEntity).isEmpty()) {
            Optional<StatusLv2> bySendUserAndReceiveUserCase1 = statusRepository.findBySendUserAndReceiveUser(userEntity, receivceUserById);
            Optional<StatusLv2> bySendUserAndReceiveUserCase2 = statusRepository.findBySendUserAndReceiveUser(receivceUserById, userEntity);
            if(!bySendUserAndReceiveUserCase1.isEmpty()) {
                StatusLv2 findStatusLv2 = bySendUserAndReceiveUserCase1.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                statusRepository.delete(findStatusLv2);
                return null;
            } else {
                StatusLv2 findStatusLv2 = bySendUserAndReceiveUserCase2.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                statusRepository.delete(findStatusLv2);
                return null;
            }

        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 관계를 찾을 수 없습니다.");
    }


    // 대기중인 유저 모두 조회
    @Transactional
    public List<StatusResponseDto> findAllStatus(String UserOfEmail) {

        List<StatusResponseDto> statusList = new ArrayList<>();

        Optional<UserEntity> byEmail = userRepository.findByEmail(UserOfEmail);
//        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함
//
        UserEntity userEntity = byEmail.get();

        Map<Long, StatusLv2> byReceiveUserAndSendUser = statusRepository.findByReceiveUserAndSendUser(userEntity);

        Collection<StatusLv2> values = byReceiveUserAndSendUser.values();
        for (StatusLv2 value : values) {
                // if accept 일때 추가해야함 아니면 v2 를 만들까?
                if(!value.getSendUser().equals(userEntity)) {
                    statusList.add(new StatusResponseDto(value.getReceiveUser(),value.getSendUser(), value.getStatusvalue()));
                    continue; //
                }
                statusList.add(new StatusResponseDto(value.getSendUser(), value.getReceiveUser(), value.getStatusvalue()));
        }
        return  statusList;
    }



    // 수락한 친구 모두 조회
    @Transactional
    public List<StatusResponseDto> findAcceptanceStatus(String UserOfEmail) {

        List<StatusResponseDto> statusList = new ArrayList<>();

        Optional<UserEntity> byEmail = userRepository.findByEmail(UserOfEmail);
//        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함
//
        UserEntity userEntity = byEmail.get();

        Map<Long, StatusLv2> byReceiveUserAndSendUser = statusRepository.findByReceiveUserAndSendUser(userEntity);

        Collection<StatusLv2> values = byReceiveUserAndSendUser.values();
        for (StatusLv2 value : values) {
            if(value.getStatusvalue().equals(StatusValue.acceptance)) {
                // if accept 일때 추가해야함 아니면 v2 를 만들까?
                if(!value.getSendUser().equals(userEntity)) {
                    statusList.add(new StatusResponseDto(value.getReceiveUser(),value.getSendUser(), value.getStatusvalue()));
                    continue; //
                }
                statusList.add(new StatusResponseDto(value.getSendUser(), value.getReceiveUser(), value.getStatusvalue()));
            }

        }

        return  statusList;


//        List<StatusResponseDto> statusList = new ArrayList<>();
//
//        Optional<UserEntity> byEmail = userRepository.findByEmail(UserOfEmail);
//        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함
//
//        UserEntity userEntity = byEmail.get();
//
//        List<StatusLv2> bySendUser = statusRepository.findBySendUser(userEntity);
//
//        //
//
//
//        //
//        for(StatusLv2 status : bySendUser) {
//            if(status.getStatusvalue().equals(StatusValue.acceptance)) {
//                statusList.add(new StatusResponseDto(status.getSendUser(), status.getReceiveUser(), status.getStatusvalue()));
//            }
//        }
//
//    return statusList;
    }


    // 수락한 친구 중 한명 조회
    @Transactional
    public StatusResponseDto findByEmailStatus(Long receiveId, String UserOfEmail) {

        //
        Optional<UserEntity> byEmail = userRepository.findByEmail(UserOfEmail);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함
        UserEntity userEntity = byEmail.get();

        UserEntity receiveUser = userRepository.findById(receiveId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        if(!statusRepository.findBySendUserAndReceiveUser(userEntity, receiveUser).isEmpty() || !statusRepository.findBySendUserAndReceiveUser(receiveUser ,userEntity).isEmpty()) {
            Optional<StatusLv2> bySendUserAndReceiveUserCase1 = statusRepository.findBySendUserAndReceiveUser(userEntity, receiveUser);
            Optional<StatusLv2> bySendUserAndReceiveUserCase2 = statusRepository.findBySendUserAndReceiveUser(receiveUser, userEntity);
            if(!bySendUserAndReceiveUserCase1.isEmpty()) {
                StatusLv2 findStatusLv2 = bySendUserAndReceiveUserCase1.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
              return new StatusResponseDto(findStatusLv2.getSendUser(),findStatusLv2.getReceiveUser(),findStatusLv2.getStatusvalue());
            } else {
                StatusLv2 findStatusLv2 = bySendUserAndReceiveUserCase2.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                return new StatusResponseDto(findStatusLv2.getReceiveUser(),findStatusLv2.getSendUser(),findStatusLv2.getStatusvalue());
            }

        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 관계를 찾을 수 없습니다.");
    }
}
