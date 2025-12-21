package org.example.interviewbe.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.interviewbe.services.serviceInterface.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/scores")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ScoreController {
    ScoreService scoreService;

    @GetMapping("/{res_num}")
    public ResponseEntity<?> getScoreById(@PathVariable String res_num){
        try{
            var scoreDto = scoreService.findByRegistrationNum(res_num);
            if (scoreDto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(scoreDto);
        } catch (Exception e) {
            log.error("Error retrieving score for registration number {}: {}", res_num, e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @GetMapping("/top10/groupA")
    public ResponseEntity<?> getTop10ScoresGroupA(){
        try{
            var topScores = scoreService.findTop10ScoresGroupA();
            return ResponseEntity.ok(topScores);
        } catch (Exception e) {
            log.error("Error retrieving top 10 scores for group A: {}", e.getMessage());
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
