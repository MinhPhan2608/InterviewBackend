package org.example.interviewbe.services.serviceInterface;

import org.example.interviewbe.services.dto.request.AuthRequestDTO;
import org.example.interviewbe.services.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO authenticate(AuthRequestDTO authRequest);
}
