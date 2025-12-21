package org.example.interviewbe.services.serviceInterface;

import org.example.interviewbe.models.StudentScore;
import org.example.interviewbe.services.dto.response.ScoreResponseDTO;

import java.util.List;

public interface ScoreService {
    ScoreResponseDTO findByRegistrationNum(String registrationNum);
    List<ScoreResponseDTO> findTop10ScoresGroupA();
}
