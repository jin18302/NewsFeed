package NewsFeedProject.newsfeed.status.controller;


import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
import NewsFeedProject.newsfeed.status.service.StatusServiceLv2;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("lv2/statuses")
@AllArgsConstructor
public class StatusControllerLv2 {

    private final StatusServiceLv2 statusService;

    @PostMapping
    public ResponseEntity<Void> createSingleStatus(HttpServletRequest request,
                                                   @RequestBody StatausRequestDto dto) {

        HttpSession session = request.getSession();
        Map attribute = (Map) session.getAttribute("session");
        String UserOfEmail = (String)attribute.get("email");

        return ResponseEntity.ok(statusService.createSingleStatus(dto,UserOfEmail));
    }

    @PatchMapping ("/{sendUserId}")
    public ResponseEntity<StatusResponseDto> setPairStatus(HttpServletRequest request,
                                                            @RequestBody StatausRequestDto dto) {

        HttpSession session = request.getSession();
        Map attribute = (Map) session.getAttribute("session");
        String UserOfEmail = (String)attribute.get("email");

        return ResponseEntity.ok(statusService.setPairStatus(dto,UserOfEmail));
    }

    @GetMapping
    public ResponseEntity<List<StatusResponseDto>> findAllStatus(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Map attribute = (Map) session.getAttribute("session");
        String UserOfEmail = (String)attribute.get("email");


        return ResponseEntity.ok(statusService.findAllStatus(UserOfEmail));
    }

    // 수락한 친구 모두 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StatusResponseDto>> findAcceptanceStatus(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Map attribute = (Map) session.getAttribute("session");
        String UserOfEmail = (String)attribute.get("email");


        return ResponseEntity.ok(statusService.findAcceptanceStatus(UserOfEmail));
    }

    @GetMapping("/{receiveUserId}")
    public ResponseEntity<StatusResponseDto> findByEmailStatus(HttpServletRequest request,
                                                               @PathVariable("receiveUserId") Long id) {


        HttpSession session = request.getSession();
        Map attribute = (Map) session.getAttribute("session");
        String UserOfEmail = (String)attribute.get("email");

        return ResponseEntity.ok(statusService.findByEmailStatus(id,UserOfEmail));
    }

    @DeleteMapping("/{receiveUserId}")
    public ResponseEntity<Void> deleteStatus(HttpServletRequest request,
                                             @RequestBody StatausRequestDto dto) {


        HttpSession session = request.getSession();
        Map attribute = (Map) session.getAttribute("session");
        String UserOfEmail = (String)attribute.get("email");


        statusService.deleteStatus(dto,UserOfEmail);

        return ResponseEntity.noContent().build();
    }




}
