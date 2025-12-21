package org.example.interviewbe.repositories;

import org.example.interviewbe.models.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepo extends JpaRepository<StudentScore, Integer> {

}
