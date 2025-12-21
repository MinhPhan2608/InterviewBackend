package org.example.interviewbe.services.serviceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.interviewbe.models.StudentScore;
import org.example.interviewbe.repositories.ScoreRepo;
import org.example.interviewbe.services.dto.ScoreResponseDTO;
import org.example.interviewbe.services.serviceInterface.ScoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class ScoreServiceImpl implements ScoreService {
    ScoreRepo scoreRepo;

    @Override
    public ScoreResponseDTO findByRegistrationNum(String registrationNum) {
        return null;
    }

    @Override
    public void insertBatchScores(List<StudentScore> studentScores) {
        scoreRepo.saveAll(studentScores);
    }
}
