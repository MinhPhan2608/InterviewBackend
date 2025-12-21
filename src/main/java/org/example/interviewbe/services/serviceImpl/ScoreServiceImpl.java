package org.example.interviewbe.services.serviceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.interviewbe.models.StudentScore;
import org.example.interviewbe.repositories.ScoreRepo;
import org.example.interviewbe.services.dto.response.ScoreResponseDTO;
import org.example.interviewbe.services.mapper.ScoreMapper;
import org.example.interviewbe.services.serviceInterface.ScoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ScoreServiceImpl implements ScoreService {
    ScoreRepo scoreRepo;
    ScoreMapper scoreMapper;
    @Override
    public ScoreResponseDTO findByRegistrationNum(String registrationNum) {
        try{
            StudentScore studentScore = scoreRepo.findByRegistrationNumber(registrationNum);
            if (studentScore == null) {
                return null;
            }
            return scoreMapper.toDto(studentScore);
        } catch (Exception e) {
            log.error("Error finding student score by registration number {}: {}", registrationNum, e.getMessage());
            throw new RuntimeException("Failed to find student score", e);
        }
    }

    @Override
    public void insertBatchScores(List<StudentScore> studentScores) {
        scoreRepo.saveAll(studentScores);
    }
}
