package org.example.interviewbe.services.serviceImpl;

import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.interviewbe.config.security.JwtService;
import org.example.interviewbe.models.Account;
import org.example.interviewbe.repositories.AccountRepo;
import org.example.interviewbe.services.dto.request.AuthRequestDTO;
import org.example.interviewbe.services.dto.response.AuthResponseDTO;
import org.example.interviewbe.services.serviceInterface.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level= AccessLevel.PRIVATE)
public class AuthServiceImpl implements AuthService {

    AccountRepo repository;
    JwtService jwtService;
    AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO authRequest) {
        Account account = repository.findByUsername(authRequest.getUsername());
        if (account == null){
            throw new EntityNotFoundException("User not found");
        }
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                ));
        if (authentication.isAuthenticated()){
            return new AuthResponseDTO(
                    jwtService.generateAccessToken(account),
                    jwtService.generateRefreshToken(account));
        }
        else throw new RuntimeException("Invalid login credentials");
    }

    @Override
    public AuthResponseDTO refresh(String refreshToken) {
        String token = refreshToken.replace("Bearer ", "");
        if (jwtService.validateRefreshToken(token)){
            String username = jwtService.extractUserName(token);
            Account account = repository.findByUsername(username);
            if (account == null){
                throw new EntityNotFoundException("User not found");
            }
            return new AuthResponseDTO(
                    jwtService.generateAccessToken(account),
                    ""
            );
        }
        throw new JwtException("Invalid refresh token");
    }
}
