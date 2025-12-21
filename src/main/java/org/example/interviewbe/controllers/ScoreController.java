package org.example.interviewbe.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.interviewbe.controllers.base.ApiResponse;
import org.example.interviewbe.controllers.base.BaseController;
import org.example.interviewbe.services.dto.response.ScoreResponseDTO;
import org.example.interviewbe.services.serviceInterface.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/scores")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ScoreController extends BaseController {
    ScoreService scoreService;

    @GetMapping("/{res_num}")
    public ResponseEntity<ApiResponse<ScoreResponseDTO>> getScoreById(@PathVariable String res_num){
        var scoreDto = scoreService.findByRegistrationNum(res_num);
        return success(scoreDto);

    }

    @GetMapping("/top10/groupA")
    public ResponseEntity<ApiResponse<List<ScoreResponseDTO>>> getTop10ScoresGroupA(){
        var topScores = scoreService.findTop10ScoresGroupA();
        return success(topScores);
    }
}
