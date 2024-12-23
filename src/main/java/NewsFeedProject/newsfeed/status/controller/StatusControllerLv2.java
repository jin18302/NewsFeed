//package NewsFeedProject.newsfeed.status.controller;
//
//
//import NewsFeedProject.newsfeed.status.dto.StatausRequestDto;
//import NewsFeedProject.newsfeed.status.dto.StatusResponseDto;
//import NewsFeedProject.newsfeed.status.service.StatusServiceLv1;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/statuses")
//@AllArgsConstructor
//public class StatusControllerLv2 {
//
//    private StatusServiceLv1 statusServiceLv1;
//
//    @PostMapping
//    public ResponseEntity<StatusResponseDto> createSingleStatus(@RequestBody StatausRequestDto dto) {
//
//        return ResponseEntity.ok(statusServiceLv1.createSingleStatus(dto));
//    }
//
//    @PatchMapping ("/{sendUserId}")
//    public ResponseEntity<StatusResponseDto> setPairStatus(@PathVariable("sendUserId") Long sendUserId,
//                                                          @RequestBody StatausRequestDto dto) {
//
//        return ResponseEntity.ok(statusServiceLv1.setPairStatus(sendUserId,dto));
//    }
//
//    @DeleteMapping("/{receiveUserId}")
//    public ResponseEntity<Void> deleteStatus(@PathVariable("receiveUserId") Long receiveUserId,
//                                                          @RequestBody StatausRequestDto dto) {
//
//        statusServiceLv1.deleteStatus(receiveUserId,dto);
//
//        return ResponseEntity.noContent().build();
//    }
//
//
//
//
//}
