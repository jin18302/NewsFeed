package NewsFeedProject.newsfeed.status.service;

import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
import NewsFeedProject.newsfeed.status.entity.StatusLv2;
import NewsFeedProject.newsfeed.status.repository.StatusRepositoryLv2;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@AllArgsConstructor
public class StatusServiceLv2 {

    private StatusRepositoryLv2 statusRepository;


    public StatusResponseDto createSingleStatus(StatausRequestDto dto) {

        StatusLv2 newStatusLv2 = StatusLv2.createStatus(dto.getSendUser(), dto.getSendUser());

        statusRepository.save(newStatusLv2);

        StatusResponseDto statusResponseDto = new StatusResponseDto(dto.getSendUser(), dto.getSendUser());

        return statusResponseDto;
    }


    @Transactional
    public StatusResponseDto setPairStatus(StatausRequestDto dto) {

        Optional<StatusLv2> bySendUserAndReceiveUser = statusRepository.findBySendUserAndReceiveUser(dto.getSendUser(), dto.getReceiveUser());

        StatusLv2 findStatusLv2 = bySendUserAndReceiveUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        findStatusLv2.setStatusvalue(dto.getStatusvalue());

        return new StatusResponseDto(findStatusLv2.getSendUser(),findStatusLv2.getReceiveUser(),findStatusLv2.getStatusvalue());
    }

    public Void deleteStatus(StatausRequestDto dto) {

        Optional<StatusLv2> byReceiveUserId = statusRepository.findBySendUserAndReceiveUser(dto.getSendUser(),dto.getReceiveUser());

        StatusLv2 findStatusLv2 = byReceiveUserId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        statusRepository.delete(findStatusLv2);

        return null;
    }
}
