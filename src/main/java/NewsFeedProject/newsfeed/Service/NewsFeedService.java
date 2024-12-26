package NewsFeedProject.newsfeed.Service;

import NewsFeedProject.newsfeed.Dto.NewsFeedPageResponseDto;
import NewsFeedProject.newsfeed.Dto.NewsFeedRequestDto;
import NewsFeedProject.newsfeed.Dto.NewsFeedResponseDto;
import NewsFeedProject.newsfeed.Entity.Member;
import NewsFeedProject.newsfeed.Entity.NewsFeed;
import NewsFeedProject.newsfeed.Repository.MemberRepository;
import NewsFeedProject.newsfeed.Repository.NewsFeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

//import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;
    private final MemberRepository memberRepository;


    public NewsFeedResponseDto create(String title, String contents,String email) {

        Member member = memberRepository.findByEmail(email).get(); //

        NewsFeed newsFeed = new NewsFeed(title, contents, member);

        NewsFeed saveNewsFeed = newsFeedRepository.save(newsFeed);

        return toDto(newsFeed);
    }

    public Page<NewsFeedPageResponseDto> getPaginatedNewsFeeds(Pageable pageable){
        Page<NewsFeedPageResponseDto> newsFeeds = newsFeedRepository.findAllWithPagination(pageable);
        return newsFeeds;
    }

    public NewsFeedResponseDto getNewsFeedById(Long id) {
        NewsFeed newsFeed = findNewsFeedById(id);
        return toDto(newsFeed);
    }

    public NewsFeedResponseDto update(Long id, NewsFeedRequestDto requestDto, String email) {

        Member member = memberRepository.findByEmail(email).get();//Todo 로그인 된거라 orelsethrow 생략

        NewsFeed newsFeed = findNewsFeedById(id);
        if(newsFeed.getMember()==member) {
            newsFeed.updateNewsFeed(requestDto);
            newsFeedRepository.save(newsFeed);

            return toDto(newsFeed);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"사용자 권한이 없습니다");
        }
    }

    public void delete(Long id,String email) {

        Member member = memberRepository.findByEmail(email).get();//Todo 로그인 된거라 orelsethrow 생략

        NewsFeed newsFeed = findNewsFeedById(id);
        if(newsFeed.getMember()==member) {
            newsFeedRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"사용자 권한이 없습니다");
        }

    }

    private NewsFeed findNewsFeedById(Long id) {
        return newsFeedRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id" + id));
    }

    private NewsFeedResponseDto toDto(NewsFeed newsFeed) {
        return NewsFeedResponseDto.toDto(newsFeed);
    }
}
