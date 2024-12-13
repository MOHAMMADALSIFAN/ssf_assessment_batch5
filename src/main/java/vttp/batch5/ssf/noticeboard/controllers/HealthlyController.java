package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp.batch5.ssf.noticeboard.services.NoticeService;

@RestController
public class HealthlyController {

  @Autowired
  NoticeService noticeService;

   @GetMapping(path = "/status")
    public ResponseEntity<String> healthstatus() {
        Boolean isHealthy = noticeService.isHealthy();
        try {
            if (isHealthy) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{}");
            } else {
                return ResponseEntity
                        .status(HttpStatus.SERVICE_UNAVAILABLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{}");
            }
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{}");
        }
    }
}
