package org.example.interviewbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.interviewbe.controllers.base.BaseResponse;
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
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Statistic Controller", description = "APIs for retrieving statistics of scores")
public class StatisticController extends BaseController {
    StatisticService service;

    @Operation(summary = "Get scores statistics by subject",
            description = "Retrieve statistical data in 4 groups of scores for each subject.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    @GetMapping("/")
    public ResponseEntity<BaseResponse<List<StatisticResponseDTO>>> getAllStatisticsBySubject() {
        var statistics = service.getAllStatisticsBySubject();
        return success(statistics);
    }
}
