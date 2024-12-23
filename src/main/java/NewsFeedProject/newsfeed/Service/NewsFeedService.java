package NewsFeedProject.newsfeed.Service;

import NewsFeedProject.newsfeed.dto.NewsFeedResponseDto;
import NewsFeedProject.newsfeed.entity.NewsFeed;
import NewsFeedProject.newsfeed.repository.NewsFeedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
