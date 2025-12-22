package org.example.interviewbe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.interviewbe.controllers.base.BaseController;
import org.example.interviewbe.controllers.base.BaseResponse;
import org.example.interviewbe.services.dto.request.AuthRequestDTO;
import org.example.interviewbe.services.dto.response.AuthResponseDTO;
import org.example.interviewbe.services.serviceInterface.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Authentication Controller", description = "Endpoints for basic authentication")
public class AuthController extends BaseController {
    AuthService authService;

    @Operation(summary = "Login",
    description = "Authenticate user with username and password",
            security = {})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login success"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponseDTO>> login(@Valid @RequestBody AuthRequestDTO request){
        var result = authService.authenticate(request);
        return success(result);
    }

    @Operation(summary = "Refresh access token",
    description = "Refresh access token using refresh token",
            security = {})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @GetMapping("/refresh")
    public ResponseEntity<BaseResponse<AuthResponseDTO>> refresh(
            @RequestHeader("Authorization")
            @NotBlank(message = "Registration number must not be empty")
            String refreshToken
    ){
        var result = authService.refresh(refreshToken);
        return success(result);
    }
}
