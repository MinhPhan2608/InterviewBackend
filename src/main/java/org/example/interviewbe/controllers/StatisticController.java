package org.example.interviewbe.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.interviewbe.controllers.base.ApiResponse;
import org.example.interviewbe.controllers.base.BaseController;
import org.example.interviewbe.services.dto.response.StatisticResponseDTO;
import org.example.interviewbe.services.serviceInterface.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticController extends BaseController {
    StatisticService service;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<StatisticResponseDTO>>> getAllStatisticsBySubject() {
        var statistics = service.getAllStatisticsBySubject();
        return success(statistics);
    }
}
