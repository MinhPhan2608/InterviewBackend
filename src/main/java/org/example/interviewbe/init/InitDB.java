package org.example.interviewbe.init;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.interviewbe.models.Statistic;
import org.example.interviewbe.models.StudentScore;
import org.example.interviewbe.services.serviceInterface.ScoreService;
import org.example.interviewbe.services.serviceInterface.StatisticService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.parseFloat;

@Component
@Order(1)
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class InitDB implements CommandLineRunner {
    ScoreService scoreService;
    StatisticService statisticService;



    static String DATA_SOURCE = "data/diem_thi_thpt_2024.csv";
    static int BATCH_SIZE =1000;
    static List<String> SUBJECT_ARR = List.of("math", "literature", "english", "physics",
            "chemistry", "biology", "history", "geography", "gdcd"
    );
    static List<Statistic> statistics = new ArrayList<>();
    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i<9; i++){
            Statistic stat = Statistic.builder()
                    .subject(SUBJECT_ARR.get(i))
                    .group1(0)
                    .group2(0)
                    .group3(0)
                    .group4(0)
                    .build();
            statistics.add(stat);
        }
        InputStream input = getClass().getClassLoader().getResourceAsStream(DATA_SOURCE);
        if (input == null) {
            log.warn("Data source not found");
            return;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String line;
        br.readLine();
        List<StudentScore> batch = new ArrayList<>(BATCH_SIZE);
        int totalInserted = 0;
        while ((line = br.readLine()) != null) {
            String[] cols = line.split(",", -1);
            StudentScore score = StudentScore.builder()
                    .registrationNumber(cols[0])
                    .math(parseFloat(cols[1]))
                    .literature(parseFloat(cols[2]))
                    .language(parseFloat(cols[3]))
                    .physics(parseFloat(cols[4]))
                    .chemistry(parseFloat(cols[5]))
                    .biology(parseFloat(cols[6]))
                    .history(parseFloat(cols[7]))
                    .geography(parseFloat(cols[8]))
                    .gdcd(parseFloat(cols[9]))
                    .languageCode(cols[10])
                    .build();
            score.setTotalA();
            try {
                addStatistic(statistics, score);
            } catch (Exception e) {
                log.error("Error updating statistics for score: {}", score, e);
            }
            batch.add(score);
            if (batch.size() == BATCH_SIZE) {
                scoreService.insertBatchScores(batch);
                totalInserted += batch.size();
                batch.clear();
                log.info("Inserted: {}", totalInserted);
            }
        }
        if (!batch.isEmpty()) {
            scoreService.insertBatchScores(batch);
            totalInserted += batch.size();
        }
        statisticService.insertBySubject(statistics);
    }
    void addStatistic(List<Statistic> stats, StudentScore score) {
        updateStat(stats, "math", score.getMath());
        updateStat(stats, "literature", score.getLiterature());
        updateStat(stats, "english", score.getLanguage());
        updateStat(stats, "physics", score.getPhysics());
        updateStat(stats, "chemistry", score.getChemistry());
        updateStat(stats, "biology", score.getBiology());
        updateStat(stats, "history", score.getHistory());
        updateStat(stats, "geography", score.getGeography());
        updateStat(stats, "gdcd", score.getGdcd());
    }

    private void updateStat(List<Statistic> stats, String subject, Float score) {
        if (score == null) return;
        Statistic stat = stats.stream()
                .filter(s -> s.getSubject().equals(subject))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Statistic not found for " + subject));
        if (score >= 8) {
            stat.setGroup1(stat.getGroup1() + 1);
        } else if (score >= 6) {
            stat.setGroup2(stat.getGroup2() + 1);
        } else if (score >= 4) {
            stat.setGroup3(stat.getGroup3() + 1);
        } else {
            stat.setGroup4(stat.getGroup4() + 1);
        }
    }
}
