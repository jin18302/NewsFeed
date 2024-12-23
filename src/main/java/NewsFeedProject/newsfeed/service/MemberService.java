package NewsFeedProject.newsfeed.service;


import NewsFeedProject.newsfeed.config.PasswordEncoder;
import NewsFeedProject.newsfeed.dto.MemberResponseDto;
import NewsFeedProject.newsfeed.dto.MemberSimpleResponseDto;
import NewsFeedProject.newsfeed.dto.MemberUpdateResponseDto;
import NewsFeedProject.newsfeed.entity.Member;
import NewsFeedProject.newsfeed.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto signUp(String name, String email, String password, String birthdate, String nickname, String comment){


        if(memberRepository.findByEmail(email).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 사용중인 이메일 입니다");
        }

        Member member = new Member(name, email, password, birthdate, nickname, comment);

        Member saveMember = memberRepository.save(member);

        return MemberResponseDto.toDto(saveMember);


    }

    public List<MemberSimpleResponseDto> findAllMembers(){
        return memberRepository.findAll()
                .stream()
                .map(MemberSimpleResponseDto::summaryDto)
                .toList();
    }

    public MemberSimpleResponseDto findById(Long id){

        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일치 하는 정보가 없습니다!!");
        }

        Member findMember = optionalMember.get();

        return MemberSimpleResponseDto.summaryDto(findMember);

    }

    public MemberUpdateResponseDto updateMember(Long id, String nickname, String comment, String password){

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        if(!findMember.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        findMember.setNickname(nickname);
        findMember.setComment(comment);

        Member updateMember = memberRepository.save(findMember);

        return MemberUpdateResponseDto.updateDto(updateMember);


    }

    public void updatePassword(Long id, String oldPassword, String newPassword){

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        if(!findMember.getPassword().equals(oldPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

        } if(findMember.getPassword().equals(newPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "같은 비밀번호는 사용할 수 없습니다.");
        }

        findMember.setPassword(newPassword);


        memberRepository.save(findMember);


    }


    public void deleteMember(Long id, String password){

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        if(!findMember.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다");
        }

        memberRepository.delete(findMember);
    }



}






