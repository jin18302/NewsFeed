package NewsFeedProject.newsfeed.controller;

import NewsFeedProject.newsfeed.dto.*;
import NewsFeedProject.newsfeed.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @PostMapping("signup")
    public ResponseEntity<MemberResponseDto> signUp(@Valid @RequestBody MemberRequestDto dto){
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
    public ResponseEntity<MemberSimpleResponseDto> findById(@PathVariable Long id){

        MemberSimpleResponseDto memberSummaryDto = memberService.findById(id);

        return new ResponseEntity<>(memberSummaryDto, HttpStatus.OK);

    }


    @PatchMapping("/{id}/update")
    public ResponseEntity<MemberUpdateResponseDto> updateMember(
            @PathVariable Long id,
            @RequestBody MemberRequestDto dto
    ){
        MemberUpdateResponseDto updateMember = memberService.updateMember(id, dto.getNickname(), dto.getComment(), dto.getPassword());

        return new ResponseEntity<>(updateMember, HttpStatus.OK);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<MemberResponseDto> updatePassword(
            @PathVariable Long id,
            @RequestBody PasswordRequestDto dto
    ){
        memberService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long id,
            @RequestBody MemberRequestDto dto

    ){

        memberService.deleteMember(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
