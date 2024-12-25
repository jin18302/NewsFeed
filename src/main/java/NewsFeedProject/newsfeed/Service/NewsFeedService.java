package NewsFeedProject.newsfeed.Service;

import NewsFeedProject.newsfeed.Dto.NewsFeedRequestDto;
import NewsFeedProject.newsfeed.Dto.NewsFeedResponseDto;
import NewsFeedProject.newsfeed.Entity.NewsFeed;
import NewsFeedProject.newsfeed.Repository.NewsFeedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

//import java.util.List;

@Service
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;

    public NewsFeedService(NewsFeedRepository newsFeedRepository) {
        this.newsFeedRepository = newsFeedRepository;
    }

    public NewsFeedResponseDto create(String title, String contents) {

        NewsFeed newsFeed = new NewsFeed(title, contents);

        NewsFeed saveNewsFeed = newsFeedRepository.save(newsFeed);

        return toDto(newsFeed);
    }

    public Page<NewsFeed> getNewsFeed(Pageable pageable) {
        Page<NewsFeed> newsFeedPageList = newsFeedRepository.findAllByOrderByCreatedAtDesc(pageable);
        return newsFeedPageList;
    }

    public NewsFeedResponseDto getNewsFeedById(Long id) {
        NewsFeed newsFeed = findNewsFeedById(id);
        return toDto(newsFeed);
    }

    public NewsFeedResponseDto update(Long id, NewsFeedRequestDto requestDto) {

        NewsFeed newsFeed = findNewsFeedById(id);
        newsFeed.updateNewsFeed(requestDto);
        newsFeedRepository.save(newsFeed);

        return toDto(newsFeed);
    }

    public void delete(Long id) {
        newsFeedRepository.deleteById(id);
    }

    private NewsFeed findNewsFeedById(Long id) {
        return newsFeedRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id" + id));
    }

    private NewsFeedResponseDto toDto(NewsFeed newsFeed) {
        return NewsFeedResponseDto.toDto(newsFeed);
    }
}
