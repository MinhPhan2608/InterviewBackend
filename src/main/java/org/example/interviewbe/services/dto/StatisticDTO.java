package org.example.interviewbe.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticDTO {
    String subject;
    int group1;
    int group2;
    int group3;
    int group4;
}
