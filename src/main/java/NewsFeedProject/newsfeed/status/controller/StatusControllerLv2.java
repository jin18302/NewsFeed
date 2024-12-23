package NewsFeedProject.newsfeed.status.controller;


import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
import NewsFeedProject.newsfeed.status.service.StatusServiceLv1;
import NewsFeedProject.newsfeed.status.service.StatusServiceLv2;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statuses")
@AllArgsConstructor
public class StatusControllerLv2 {

    private StatusServiceLv2 statusService;

    @PostMapping
    public ResponseEntity<StatusResponseDto> createSingleStatus(@RequestBody StatausRequestDto dto) {

        return ResponseEntity.ok(statusService.createSingleStatus(dto));
    }

    @PatchMapping ("/{sendUserId}")
    public ResponseEntity<StatusResponseDto> setPairStatus(@RequestBody StatausRequestDto dto) {

        return ResponseEntity.ok(statusService.setPairStatus(dto));
    }

    @DeleteMapping("/{receiveUserId}")
    public ResponseEntity<Void> deleteStatus(@RequestBody StatausRequestDto dto) {

        statusService.deleteStatus(dto);

        return ResponseEntity.noContent().build();
    }




}
