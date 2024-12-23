package NewsFeedProject.newsfeed.status.service;

import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
import NewsFeedProject.newsfeed.status.entity.Status;
import NewsFeedProject.newsfeed.status.repository.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@AllArgsConstructor
public class StatusServiceLv1 {

    private StatusRepository statusRepository;


    public StatusResponseDto createSingleStatus(StatausRequestDto dto) {

        Status newStatus = Status.createStatus(dto.getSendUser(), dto.getSendUser());

        statusRepository.save(newStatus);

        StatusResponseDto statusResponseDto = new StatusResponseDto(dto.getSendUser(), dto.getSendUser());

        return statusResponseDto;
    }

    public Void deleteStatus(StatausRequestDto dto) {

        Optional<Status> byReceiveUserId = statusRepository.findBySendUserAndReceiveUser(dto.getSendUser(),dto.getReceiveUser());

        Status findStatus = byReceiveUserId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        statusRepository.delete(findStatus);

        return null;
    }
}
