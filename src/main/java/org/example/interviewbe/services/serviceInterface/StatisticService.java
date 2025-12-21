package org.example.interviewbe.services.serviceInterface;

import org.example.interviewbe.models.Statistic;
import org.example.interviewbe.services.dto.response.StatisticResponseDTO;

import java.util.List;

public interface StatisticService {
    List<StatisticResponseDTO> getAllStatisticsBySubject();
}
