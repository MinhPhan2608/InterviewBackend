package org.example.interviewbe.services.serviceImpl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.interviewbe.models.Statistic;
import org.example.interviewbe.repositories.StatisticRepo;
import org.example.interviewbe.services.dto.response.StatisticResponseDTO;
import org.example.interviewbe.services.mapper.StatisticsMapper;
import org.example.interviewbe.services.serviceInterface.StatisticService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class StatisticServiceImpl implements StatisticService {
    StatisticRepo repo;
    StatisticsMapper mapper;

    @Override
    public List<StatisticResponseDTO> getAllStatisticsBySubject() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
