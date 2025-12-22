package org.example.interviewbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.interviewbe.controllers.base.BaseResponse;
import org.example.interviewbe.controllers.base.BaseController;
import org.example.interviewbe.services.dto.response.ScoreResponseDTO;
import org.example.interviewbe.services.serviceInterface.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/scores")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Score Controller", description = "APIs for retrieving student scores")
public class ScoreController extends BaseController {
    ScoreService scoreService;

    @Operation(
            summary = "Get score by registration number",
            description = "Retrieve the score details for a student using their registration number. Registration number must be 8 digits.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully get score"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    @GetMapping("/{res_num}")
    public ResponseEntity<BaseResponse<ScoreResponseDTO>> getScoreById(
            @PathVariable @NotBlank(message = "Registration number must not be empty")
            @Pattern(regexp = "\\d{8}", message = "Registration number must be exactly 8 digits") String res_num){
        var scoreDto = scoreService.findByRegistrationNum(res_num);
        return success(scoreDto);

    }

    @Operation(
            summary = "Get top 10 in Group A",
            description = "Retrieve the top 10 highest scores among students in Group A.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    @GetMapping("/top10/groupA")
    public ResponseEntity<BaseResponse<List<ScoreResponseDTO>>> getTop10ScoresGroupA(){
        var topScores = scoreService.findTop10ScoresGroupA();
        return success(topScores);
    }
}
