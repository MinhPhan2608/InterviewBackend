package org.example.interviewbe.services.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScoreResponseDTO {
    String registrationNumber;
    Float math;
    Float literature;
    Float language;
    Float physics;
    Float chemistry;
    Float biology;
    Float history;
    Float geography;
    Float gdcd;
    String languageCode;
}
