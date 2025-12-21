ALTER TABLE statistic
    ADD CONSTRAINT uc_statistic_mon_hoc UNIQUE (mon_hoc);

ALTER TABLE statistic
    ALTER COLUMN group1 SET NOT NULL;

ALTER TABLE statistic
    ALTER COLUMN group2 SET NOT NULL;

ALTER TABLE statistic
    ALTER COLUMN group3 SET NOT NULL;

ALTER TABLE statistic
    ALTER COLUMN group4 SET NOT NULL;
