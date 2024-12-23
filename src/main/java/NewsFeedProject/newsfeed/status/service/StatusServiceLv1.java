package NewsFeedProject.newsfeed.status.service;

import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
import NewsFeedProject.newsfeed.status.entity.StatusLv1;
import NewsFeedProject.newsfeed.status.repository.StatusRepositoryLv1;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@AllArgsConstructor
public class StatusServiceLv1 {

    private StatusRepositoryLv1 statusRepositoryLv1;


    public StatusResponseDto createSingleStatus(StatausRequestDto dto) {

        StatusLv1 newStatusLv1 = StatusLv1.createStatus(dto.getSendUser(), dto.getSendUser());

        statusRepositoryLv1.save(newStatusLv1);

        StatusResponseDto statusResponseDto = new StatusResponseDto(dto.getSendUser(), dto.getSendUser());

        return statusResponseDto;
    }

    public Void deleteStatus(StatausRequestDto dto) {

        Optional<StatusLv1> byReceiveUserId = statusRepositoryLv1.findBySendUserAndReceiveUser(dto.getSendUser(),dto.getReceiveUser());

        StatusLv1 findStatusLv1 = byReceiveUserId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        statusRepositoryLv1.delete(findStatusLv1);

        return null;
    }
}
