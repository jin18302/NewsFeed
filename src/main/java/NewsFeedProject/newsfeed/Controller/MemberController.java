package NewsFeedProject.newsfeed.Controller;


import NewsFeedProject.newsfeed.Dto.*;
import NewsFeedProject.newsfeed.Service.MemberService;
import NewsFeedProject.newsfeed.filter.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup") //Todo uri 변경
    public ResponseEntity<MemberResponseDto> signUp(@Valid @RequestBody MemberRequestDto dto){
        log.info("멤버 만들기 확인합니다.");
        MemberResponseDto memberResponseDto =
                memberService.signUp(
                        dto.getName(),
                        dto.getEmail(),
                        dto.getPassword(),
                        dto.getBirthdate(),
                        dto.getNickname(),
                        dto.getComment()
                );
        return new ResponseEntity<>(memberResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MemberSimpleResponseDto>> findAllMembers(){

        List<MemberSimpleResponseDto> responseDtoList = memberService.findAllMembers();

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberSimpleResponseDto> findById(@PathVariable("id") Long id){ //Todo ("id") 명시

        MemberSimpleResponseDto memberSummaryDto = memberService.findById(id);

        return new ResponseEntity<>(memberSummaryDto, HttpStatus.OK);

    }


    @PatchMapping("/{id}/update") //Todo restful 하지 않음 바꿀지 고민..
    public ResponseEntity<MemberUpdateResponseDto> updateMember(
            @RequestHeader("Authorization") String authorization,
            @RequestBody MemberRequestDto dto
    ){

        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        MemberUpdateResponseDto updateMember = memberService.updateMember(email, dto.getNickname(), dto.getComment(), dto.getPassword());

        return new ResponseEntity<>(updateMember, HttpStatus.OK);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<MemberResponseDto> updatePassword(
            @RequestHeader("Authorization") String authorization,
            @RequestBody PasswordRequestDto dto
    ){

        String token = authorization.substring(7);

        Claims claims = jwtTokenProvider.validateToken(token);

        String email = claims.getSubject();

        memberService.updatePassword(email, dto.getOldPassword(), dto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/{id}") // Todo 아니면 이렇게 하는게 나을지 선택해야할듯 다시보니 이게 맞는듯
    public ResponseEntity<Void> deleteMember(
            @PathVariable("id") Long id,
            @RequestBody MemberRequestDto dto

    ){

        memberService.deleteMember(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
