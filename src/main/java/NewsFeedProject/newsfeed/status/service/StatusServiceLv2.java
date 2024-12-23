//package NewsFeedProject.newsfeed.status.service;
//
//import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
//import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
//import NewsFeedProject.newsfeed.status.entity.Status;
//import NewsFeedProject.newsfeed.status.repository.StatusRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//
//@Service
//@AllArgsConstructor
//public class StatusServiceLv2 {
//
//    private StatusRepository statusRepository;
//
//
//    public StatusResponseDto createSingleStatus(StatausRequestDto dto) {
//
//        Status newStatus = Status.createStatus(dto.getSendUser(), dto.getSendUser());
//
//        statusRepository.save(newStatus);
//
//        StatusResponseDto statusResponseDto = new StatusResponseDto(dto.getSendUser(), dto.getSendUser());
//
//        return statusResponseDto;
//    }
//
//    public StatusResponseDto setPairStatus(Long sendUserId, StatausRequestDto dto) {
//
//        return null;
//    }
//
//    public Void deleteStatus(Long receiveUserId, StatausRequestDto dto) {
//
//
//
//        return null;
//    }
//}
