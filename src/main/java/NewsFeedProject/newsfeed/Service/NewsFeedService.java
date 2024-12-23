package NewsFeedProject.newsfeed.Service;

import NewsFeedProject.newsfeed.dto.NewsFeedResponseDto;
import NewsFeedProject.newsfeed.entity.NewsFeed;
import NewsFeedProject.newsfeed.repository.NewsFeedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;

    public NewsFeedService(NewsFeedRepository newsFeedRepository) {
        this.newsFeedRepository = newsFeedRepository;
    }

    public NewsFeedResponseDto save(String title, String contents) {

        NewsFeed newsFeed = new NewsFeed(title, contents);

        NewsFeed saveNewsFeed = newsFeedRepository.save(newsFeed);

        return new NewsFeedResponseDto(saveNewsFeed.getId(), saveNewsFeed.getTitle(), saveNewsFeed.getContents());
    }

    public List<NewsFeedResponseDto> findAll() {

        return newsFeedRepository.findAll()
                .stream()
                .map(NewsFeedResponseDto::toDto)
                .toList();
    }

    public NewsFeedResponseDto findById(Long id) {

        Optional<NewsFeed> optionalNewsFeed = newsFeedRepository.findById(id);

        if (optionalNewsFeed.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id);
        }
        NewsFeed findNewsFeed = optionalNewsFeed.get();

        return new NewsFeedResponseDto(findNewsFeed.getId(), findNewsFeed.getTitle(), findNewsFeed.getContents());
    }
}
