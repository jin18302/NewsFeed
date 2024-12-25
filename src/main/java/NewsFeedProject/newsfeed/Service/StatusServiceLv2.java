package NewsFeedProject.newsfeed.Service;

import NewsFeedProject.newsfeed.Dto.StatausRequestDto;
import NewsFeedProject.newsfeed.Dto.StatusResponseDto;
import NewsFeedProject.newsfeed.Entity.Member;
import NewsFeedProject.newsfeed.Entity.StatusLv2;
import NewsFeedProject.newsfeed.Entity.StatusValue;
import NewsFeedProject.newsfeed.Repository.MemberRepository;
import NewsFeedProject.newsfeed.Repository.StatusRepositoryLv2;
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
    private final MemberRepository memberRepository;

    @Transactional
    public Void createSingleStatus(StatausRequestDto dto, String email) {


        Optional<Member> byEmail = memberRepository.findByEmail(email);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        Member member = byEmail.get();
        Member receivceMemberById = findMemberByReceiveId(dto);

        if(member==receivceMemberById){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"자신에게 요청을 보낼 수 없습니다.");
        }

        // 중복 방지
        if(!statusRepository.findBySendMemberAndReceiveMember(receivceMemberById ,member).isEmpty()  ) { // && !statusRepository.findBySendMemberAndReceiveMember(userEntity ,receivceMemberById).isEmpty()
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"이미 상대방이 팔로우 한 상태입니다. 요청을 수락 혹은 거절 하세요"); // 전제 조건 실패의 상태메세지
        }

        StatusLv2 newStatusLv2 = StatusLv2.createStatus(member, receivceMemberById);


        statusRepository.save(newStatusLv2);

        return null;
    }


    /**
     *
     * @param dto
     * @return
     */
    private Member findMemberByReceiveId(StatausRequestDto dto) {
        Optional<Member> MemberById = memberRepository.findById(dto.getOtherMemberId()); // 논리적으로는 Receive 가 맞지만, 물리적으로는 이렇게도 맞다.

        return MemberById.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public StatusResponseDto setPairStatus(StatausRequestDto dto, String email) { // 고칠부분

        // 헛갈리니 주위!
        // 이게 recevice 인거임
        Optional<Member> byEmail = memberRepository.findByEmail(email);

        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        Member member = byEmail.get();

        // 이게 send 가 되는거 // requestDto 의 필드명을 바꿔야할듯 id 를 빼오자? 엄청 헛갈림
        Member receivceMemberById = findMemberByReceiveId(dto);

        //
        if(statusRepository.findBySendMemberAndReceiveMember(member ,receivceMemberById).isEmpty()) {
            StatusLv2 statusLv2 = statusRepository.findBySendMemberAndReceiveMember(receivceMemberById, member)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            statusLv2.setStatusvalue(dto.getStatusvalue());


            return new StatusResponseDto(statusLv2.getReceiveMember(),statusLv2.getSendMember(),statusLv2.getStatusvalue());
        }

        StatusLv2 statusLv2 = statusRepository.findBySendMemberAndReceiveMember(member, receivceMemberById)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        statusLv2.setStatusvalue(dto.getStatusvalue());

        return new StatusResponseDto(statusLv2.getSendMember(),statusLv2.getReceiveMember(),statusLv2.getStatusvalue());
    }

    @Transactional
    public Void deleteStatus(StatausRequestDto dto, String email) {

        Optional<Member> byEmail = memberRepository.findByEmail(email);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함

        Member member = byEmail.get();
        Member receivceMemberById = findMemberByReceiveId(dto);

        // 관계를 양방향 확인 - 데이블에 저장된 데이터는 한쪽 기준으로 검색이 되므로 양쪽으로 다 확인 필요
        if(!statusRepository.findBySendMemberAndReceiveMember(member, receivceMemberById).isEmpty() || !statusRepository.findBySendMemberAndReceiveMember(receivceMemberById ,member).isEmpty()) {
            Optional<StatusLv2> bySendMemberAndReceiveMemberCase1 = statusRepository.findBySendMemberAndReceiveMember(member, receivceMemberById);
            Optional<StatusLv2> bySendMemberAndReceiveMemberCase2 = statusRepository.findBySendMemberAndReceiveMember(receivceMemberById, member);
            if(!bySendMemberAndReceiveMemberCase1.isEmpty()) {
                StatusLv2 findStatusLv2 = bySendMemberAndReceiveMemberCase1.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                statusRepository.delete(findStatusLv2);
                return null;
            } else {
                StatusLv2 findStatusLv2 = bySendMemberAndReceiveMemberCase2.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                statusRepository.delete(findStatusLv2);
                return null;
            }

        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 관계를 찾을 수 없습니다.");
    }


    // 대기중인 유저 모두 조회
    @Transactional
    public List<StatusResponseDto> findAllStatus(String email) {

        List<StatusResponseDto> statusList = new ArrayList<>();

        Optional<Member> byEmail = memberRepository.findByEmail(email);
//        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함
//
        Member member = byEmail.get();

        Map<Long, StatusLv2> byReceiveMemberAndSendMember = statusRepository.findByReceiveMemberAndSendMember(member);

        Collection<StatusLv2> values = byReceiveMemberAndSendMember.values();
        for (StatusLv2 value : values) {
                // if accept 일때 추가해야함 아니면 v2 를 만들까?
                if(!value.getSendMember().equals(member)) {
                    statusList.add(new StatusResponseDto(value.getReceiveMember(),value.getSendMember(), value.getStatusvalue()));
                    continue; //
                }
                statusList.add(new StatusResponseDto(value.getSendMember(), value.getReceiveMember(), value.getStatusvalue()));
        }
        return  statusList;
    }



    // 수락한 친구 모두 조회
    @Transactional
    public List<StatusResponseDto> findAcceptanceStatus(String email) {

        List<StatusResponseDto> statusList = new ArrayList<>();

        Optional<Member> byEmail = memberRepository.findByEmail(email);
//        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함
//
        Member member = byEmail.get();

        Map<Long, StatusLv2> byReceiveMemberAndSendMember = statusRepository.findByReceiveMemberAndSendMember(member);

        Collection<StatusLv2> values = byReceiveMemberAndSendMember.values();
        for (StatusLv2 value : values) {
            if(value.getStatusvalue().equals(StatusValue.acceptance)) {
                // if accept 일때 추가해야함 아니면 v2 를 만들까?
                if(!value.getSendMember().equals(member)) {
                    statusList.add(new StatusResponseDto(value.getReceiveMember(),value.getSendMember(), value.getStatusvalue()));
                    continue; //
                }
                statusList.add(new StatusResponseDto(value.getSendMember(), value.getReceiveMember(), value.getStatusvalue()));
            }

        }

        return  statusList;

    }


    // 수락한 친구 중 한명 조회
    @Transactional
    public StatusResponseDto findByEmailStatus(StatausRequestDto dto, String email) {

        //
        Optional<Member> byEmail = memberRepository.findByEmail(email);
        // byEmail.orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)); // 생략 - 로그인 기능에 포함
        Member member = byEmail.get();

        Member receiveMember = memberRepository.findById(dto.getOtherMemberId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        if(!statusRepository.findBySendMemberAndReceiveMember(member, receiveMember).isEmpty() || !statusRepository.findBySendMemberAndReceiveMember(receiveMember ,member).isEmpty()) {
            Optional<StatusLv2> bySendMemberAndReceiveMemberCase1 = statusRepository.findBySendMemberAndReceiveMember(member, receiveMember);
            Optional<StatusLv2> bySendMemberAndReceiveMemberCase2 = statusRepository.findBySendMemberAndReceiveMember(receiveMember, member);

            if(!bySendMemberAndReceiveMemberCase1.isEmpty()) {
                StatusLv2 findStatusLv2 = bySendMemberAndReceiveMemberCase1.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
              return new StatusResponseDto(findStatusLv2.getSendMember(),findStatusLv2.getReceiveMember(),findStatusLv2.getStatusvalue());
            } else {
                StatusLv2 findStatusLv2 = bySendMemberAndReceiveMemberCase2.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                return new StatusResponseDto(findStatusLv2.getReceiveMember(),findStatusLv2.getSendMember(),findStatusLv2.getStatusvalue());
            }

        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 관계를 찾을 수 없습니다.");
    }
}
