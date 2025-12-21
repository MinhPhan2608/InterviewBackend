package org.example.interviewbe.services.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
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
        StudentScore studentScore = scoreRepo.findByRegistrationNumber(registrationNum);
        if (studentScore == null) {
            throw new EntityNotFoundException("Can't find scores for registration num: " + registrationNum);
        }
        return scoreMapper.toDto(studentScore);
    }

    @Override
    public List<ScoreResponseDTO> findTop10ScoresGroupA() {
        return scoreRepo.findTop10ByOrderByTotalADesc().stream()
                .map(scoreMapper::toDto)
                .toList();
    }
}
