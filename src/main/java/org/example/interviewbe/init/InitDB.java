package org.example.interviewbe.init;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.interviewbe.models.Account;
import org.example.interviewbe.repositories.AccountRepo;
import org.example.interviewbe.repositories.ScoreRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;

@Component
@Order(1)
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class InitDB implements CommandLineRunner {
    ScoreRepo scoreRepo;
    DataSource dataSource;
    AccountRepo accountRepo;
    static String DATA_SOURCE = "data/diem_thi_thpt_2024.csv";

    @Override
    public void run(String... args) throws Exception {
        if (scoreRepo.count() > 0) {
            log.info("Skipping import");
        } else {
            InputStream input = getClass()
                    .getClassLoader()
                    .getResourceAsStream(DATA_SOURCE);
            if (input == null) {
                throw new RuntimeException("Data source not found: " + DATA_SOURCE);
            }
            try (Connection conn = dataSource.getConnection();
                 Reader reader = new BufferedReader(new InputStreamReader(input))) {
                conn.setAutoCommit(false);
                try (var stmt = conn.createStatement()) {
                    stmt.execute("""
                                CREATE TABLE scores_raw(
                                registration_number VARCHAR(255),
                                maths FLOAT,
                                literature FLOAT,
                                language FLOAT,
                                physics FLOAT,
                                chemistry FLOAT,
                                biology FLOAT,
                                history FLOAT,
                                geography FLOAT,
                                gdcd FLOAT,
                                language_code VARCHAR(255))
                            """);
                }
                CopyManager copyManager = new CopyManager(conn.unwrap(BaseConnection.class));
                copyManager.copyIn("COPY scores_raw FROM STDIN WITH (FORMAT csv, HEADER true)", reader);
                log.info("Copy to temporary table completed");

                try (var stmt = conn.createStatement()) {
                    stmt.execute("""
                                INSERT INTO score (
                                    registration_number,
                                    maths, literature, language,
                                    physics, chemistry, biology,
                                    history, geography, gdcd,
                                    total_a,
                                    language_code
                                )
                                SELECT
                                    registration_number,
                                    maths, literature, language,
                                    physics, chemistry, biology,
                                    history, geography, gdcd,
                                    COALESCE(maths, 0)
                                      + COALESCE(physics, 0)
                                      + COALESCE(chemistry, 0),
                                    language_code
                                FROM scores_raw
                            """);
                }
                log.info("Insert into score done");
                try (var stmt = conn.createStatement()) {
                    stmt.execute("""
                                INSERT INTO statistic (mon_hoc, group1, group2, group3, group4)
                                          SELECT 'maths',
                                                 COUNT(*) FILTER (WHERE maths >= 8),
                                                 COUNT(*) FILTER (WHERE maths < 8 AND maths >= 6),
                                                 COUNT(*) FILTER (WHERE maths < 6 AND maths >= 4),
                                                 COUNT(*) FILTER (WHERE maths < 4)
                                          FROM score
                                          UNION ALL
                                          SELECT 'literature',
                                                 COUNT(*) FILTER (WHERE literature >= 8),
                                                 COUNT(*) FILTER (WHERE literature < 8 AND literature >= 6),
                                                 COUNT(*) FILTER (WHERE literature < 6 AND literature >= 4),
                                                 COUNT(*) FILTER (WHERE literature < 4)
                                          FROM score
                                          UNION ALL
                                          SELECT 'language',
                                                 COUNT(*) FILTER (WHERE language >= 8),
                                                 COUNT(*) FILTER (WHERE language < 8 AND language >= 6),
                                                 COUNT(*) FILTER (WHERE language < 6 AND language >= 4),
                                                 COUNT(*) FILTER (WHERE language < 4)
                                          FROM score
                                          UNION ALL
                                          SELECT 'physics',
                                                 COUNT(*) FILTER (WHERE physics >= 8),
                                                 COUNT(*) FILTER (WHERE physics < 8 AND physics >= 6),
                                                 COUNT(*) FILTER (WHERE physics < 6 AND physics >= 4),
                                                 COUNT(*) FILTER (WHERE physics < 4)
                                          FROM score
                                          UNION ALL
                                          SELECT 'chemistry',
                                                 COUNT(*) FILTER (WHERE chemistry >= 8),
                                                 COUNT(*) FILTER (WHERE chemistry < 8 AND chemistry >= 6),
                                                 COUNT(*) FILTER (WHERE chemistry < 6 AND chemistry >= 4),
                                                 COUNT(*) FILTER (WHERE chemistry < 4)
                                          FROM score                           
                                          UNION ALL
                                          SELECT 'biology',
                                                 COUNT(*) FILTER (WHERE biology >= 8),
                                                 COUNT(*) FILTER (WHERE biology < 8 AND biology >= 6),
                                                 COUNT(*) FILTER (WHERE biology < 6 AND biology >= 4),
                                                 COUNT(*) FILTER (WHERE biology < 4)
                                          FROM score
                                          UNION ALL
                                          SELECT 'history',
                                                 COUNT(*) FILTER (WHERE history >= 8),
                                                 COUNT(*) FILTER (WHERE history < 8 AND history >= 6),
                                                 COUNT(*) FILTER (WHERE history < 6 AND history >= 4),
                                                 COUNT(*) FILTER (WHERE history < 4)
                                          FROM score
                                          UNION ALL
                                          SELECT 'geography',
                                                 COUNT(*) FILTER (WHERE geography >= 8),
                                                 COUNT(*) FILTER (WHERE geography < 8 AND geography >= 6),
                                                 COUNT(*) FILTER (WHERE geography < 6 AND geography >= 4),
                                                 COUNT(*) FILTER (WHERE geography < 4)
                                          FROM score                        
                                          UNION ALL
                                          SELECT 'gdcd',
                                                 COUNT(*) FILTER (WHERE gdcd >= 8),
                                                 COUNT(*) FILTER (WHERE gdcd < 8 AND gdcd >= 6),
                                                 COUNT(*) FILTER (WHERE gdcd < 6 AND gdcd >= 4),
                                                 COUNT(*) FILTER (WHERE gdcd < 4)
                                          FROM score;
                            """);
                }
                log.info("Statistic built successfully");

                try (var stmt = conn.createStatement()) {
                    stmt.execute("DROP TABLE scores_raw;");
                }
                log.info("Temporary table dropped");

                conn.commit();
            } catch (Exception e) {
                throw new RuntimeException("Failed to seed db with csv file", e);
            }
        }
        if (accountRepo.count() == 0) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            Account account = Account.builder()
                    .username("test")
                    .password(encoder.encode("test123"))
                    .build();
            accountRepo.save(account);
            log.info("Default accounts created with username = test and test123");
        }
        else {
            log.info("Skipping account creation");
        }
    }
}
