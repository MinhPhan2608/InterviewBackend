package org.example.interviewbe.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.interviewbe.controllers.base.ApiResponse;
import org.example.interviewbe.controllers.base.BaseController;
import org.example.interviewbe.services.dto.request.AuthRequestDTO;
import org.example.interviewbe.services.dto.response.AuthResponseDTO;
import org.example.interviewbe.services.serviceInterface.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController extends BaseController {
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody AuthRequestDTO request){
        var result = authService.authenticate(request);
        return success(result);
    }
}
