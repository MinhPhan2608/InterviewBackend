package org.example.interviewbe.repositories;

import org.example.interviewbe.models.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepo extends JpaRepository<Statistic, Integer> {
}
