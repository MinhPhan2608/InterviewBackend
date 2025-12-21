package org.example.interviewbe.services.serviceInterface;

import org.example.interviewbe.models.Statistic;

import java.util.List;

public interface StatisticService {
    void insertBySubject(List<Statistic> statistics);
}
