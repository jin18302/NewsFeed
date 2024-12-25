package NewsFeedProject.newsfeed.Service;


import NewsFeedProject.newsfeed.Entity.Member;
import NewsFeedProject.newsfeed.Repository.MemberRepository;
import NewsFeedProject.newsfeed.config.PasswordEncoder;
import NewsFeedProject.newsfeed.filter.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class JwtLoginService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public String login(String email, String password) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return jwtTokenProvider.createToken(member.getEmail(), member.getName());
    }

}
