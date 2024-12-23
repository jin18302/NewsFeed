package NewsFeedProject.newsfeed.status.controller;


import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
import NewsFeedProject.newsfeed.status.service.StatusServiceLv2;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("lv2/statuses")
@AllArgsConstructor
public class StatusControllerLv2 {

    private final StatusServiceLv2 statusService;

    @PostMapping
    public ResponseEntity<Void> createSingleStatus(@RequestBody StatausRequestDto dto) {

        return ResponseEntity.ok(statusService.createSingleStatus(dto));
    }

    @PatchMapping ("/{sendUserId}")
    public ResponseEntity<StatusResponseDto> setPairStatus(@RequestBody StatausRequestDto dto) {

        return ResponseEntity.ok(statusService.setPairStatus(dto));
    }

    @GetMapping
    public ResponseEntity<List<StatusResponseDto>> findAllStatus(@RequestBody StatausRequestDto dto) {

        return ResponseEntity.ok(statusService.findAllStatus(dto));
    }

    @GetMapping("/{receiveUserId}")
    public ResponseEntity<StatusResponseDto> findByEmailStatus(@PathVariable("receiveUserId") Long id,
                                                               @RequestBody StatausRequestDto dto) {

        return ResponseEntity.ok(statusService.findByEmailStatus(dto,id));
    }

    @DeleteMapping("/{receiveUserId}")
    public ResponseEntity<Void> deleteStatus(@RequestBody StatausRequestDto dto) {

        statusService.deleteStatus(dto);

        return ResponseEntity.noContent().build();
    }




}
