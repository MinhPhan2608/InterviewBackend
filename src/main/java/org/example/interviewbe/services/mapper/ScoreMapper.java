package org.example.interviewbe.services.mapper;

import org.example.interviewbe.models.StudentScore;
import org.example.interviewbe.services.dto.response.ScoreResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScoreMapper {
    ScoreResponseDTO toDto(StudentScore entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalA", ignore = true)
    StudentScore toEntity(ScoreResponseDTO dto);
}
