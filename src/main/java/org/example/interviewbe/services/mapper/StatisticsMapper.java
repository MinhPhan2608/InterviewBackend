package org.example.interviewbe.services.mapper;

import org.example.interviewbe.models.Statistic;
import org.example.interviewbe.services.dto.response.StatisticResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatisticsMapper {
    StatisticResponseDTO toDto(Statistic statistic);

    @Mapping(target = "id", ignore = true)
    Statistic toEntity(StatisticResponseDTO statisticResponseDTO);
}
