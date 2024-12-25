package NewsFeedProject.newsfeed.status.controller;


import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
import NewsFeedProject.newsfeed.status.service.StatusServiceLv1;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("lv1/statuses")
@AllArgsConstructor
public class StatusControllerLv1 {

    private final StatusServiceLv1 statusServiceLv1;

    @PostMapping
    public ResponseEntity<Void> createSingleStatus(@RequestBody StatausRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Map attribute = (Map) session.getAttribute("session");
        String sendUserOfEmail = (String)attribute.get("email");

        return ResponseEntity.ok(statusServiceLv1.createSingleStatus(dto,sendUserOfEmail));
    }
    @DeleteMapping("/{receiveUserId}")
    public ResponseEntity<Void> deleteStatus(@RequestBody StatausRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Map attribute = (Map) session.getAttribute("session");
        String sendUserOfEmail = (String)attribute.get("email");

        statusServiceLv1.deleteStatus(dto,sendUserOfEmail);

        return ResponseEntity.noContent().build();
    }




}
