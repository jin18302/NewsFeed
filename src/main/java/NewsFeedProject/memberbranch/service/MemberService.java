package NewsFeedProject.memberbranch.service;


import NewsFeedProject.memberbranch.config.PasswordEncoder;
import NewsFeedProject.memberbranch.dto.MemberResponseDto;
import NewsFeedProject.memberbranch.dto.MemberSimpleResponseDto;
import NewsFeedProject.memberbranch.dto.MemberUpdateResponseDto;
import NewsFeedProject.memberbranch.entity.Member;
import NewsFeedProject.memberbranch.repository.MemberRepository;
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


    public MemberResponseDto signUp(String name, String email, String password, String birthdate, String nickname, String comment) {

        String encodePassword = passwordEncoder.encode(password);

        if (DeletedMemberService.isEmailDeleted(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 탈퇴한 아이디 입니다.");
        }

        if (memberRepository.findByEmail(email).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일 입니다");
        }

        Member member = new Member(name, email, encodePassword, birthdate, nickname, comment);

        Member saveMember = memberRepository.save(member);

        return MemberResponseDto.toDto(saveMember);


    }

    public List<MemberSimpleResponseDto> findAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(MemberSimpleResponseDto::summaryDto)
                .toList();
    }

    public MemberSimpleResponseDto findById(Long id) {

        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일치 하는 정보가 없습니다!!");
        }

        Member findMember = optionalMember.get();

        return MemberSimpleResponseDto.summaryDto(findMember);

    }

    public MemberUpdateResponseDto updateMember(Long id, String nickname, String comment, String password) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        findMember.setNickname(nickname);
        findMember.setComment(comment);

        Member updateMember = memberRepository.save(findMember);

        return MemberUpdateResponseDto.updateDto(updateMember);


    }

    public void updatePassword(Long id, String oldPassword, String newPassword) {

        Member findMember = memberRepository.findByIdOrElseThrow(id);

        if (oldPassword.equals(newPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "같은 비밀번호는 사용할 수 없습니다.");
        }

        String encodePassword = passwordEncoder.encode(newPassword);

        if (!passwordEncoder.matches(oldPassword, findMember.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

        }

        findMember.setPassword(encodePassword);

        memberRepository.save(findMember);

    }


    public void deleteMember(Long id, String password) {


        Member findMember = memberRepository.findByIdOrElseThrow(id);


        if (!passwordEncoder.matches(password, findMember.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다");
        }

        DeletedMemberService.addToList(findMember.getEmail());

        memberRepository.delete(findMember);

    }


}






