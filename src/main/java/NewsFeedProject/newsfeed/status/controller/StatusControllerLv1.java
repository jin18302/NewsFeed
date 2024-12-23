package NewsFeedProject.newsfeed.status.controller;


import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
import NewsFeedProject.newsfeed.status.service.StatusServiceLv1;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statuses")
@AllArgsConstructor
public class StatusControllerLv1 {

    private StatusServiceLv1 statusServiceLv1;

    @PostMapping
    public ResponseEntity<StatusResponseDto> createSingleStatus(@RequestBody StatausRequestDto dto) {

        return ResponseEntity.ok(statusServiceLv1.createSingleStatus(dto));
    }
    @DeleteMapping("/{receiveUserId}")
    public ResponseEntity<Void> deleteStatus(@RequestBody StatausRequestDto dto) {

        statusServiceLv1.deleteStatus(dto);

        return ResponseEntity.noContent().build();
    }




}
