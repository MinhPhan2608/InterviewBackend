package org.example.interviewbe.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.interviewbe.services.serviceInterface.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticController {
    StatisticService service;

    @GetMapping("/")
    public ResponseEntity<?> getAllStatisticsBySubject() {
        try {
            var statistics = service.getAllStatisticsBySubject();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
