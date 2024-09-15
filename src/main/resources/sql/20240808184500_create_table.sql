SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE country (
    name VARCHAR(255) PRIMARY KEY
);

INSERT INTO country (name) VALUES ('Türkiye');
INSERT INTO country (name) VALUES ('United States');
INSERT INTO country (name) VALUES ('United Kingdom');
INSERT INTO country (name) VALUES ('France');
INSERT INTO country (name) VALUES ('Germany');

CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       user_level INT NOT NULL,
                       user_coin INT NOT NULL,
                       country_name VARCHAR(255) NOT NULL,
                       CONSTRAINT fk_country
                           FOREIGN KEY (country_name)
                               REFERENCES country(name)
);

CREATE TABLE tournament (
                            tournament_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            tournament_date DATE NOT NULL
);
CREATE TABLE tournamentgroup (
                                 tournament_group_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 is_started BOOLEAN NOT NULL,
                                 has_ended BOOLEAN NOT NULL,
                                 tournament_id BIGINT NOT NULL,
                                 CONSTRAINT fk_tournament
                                     FOREIGN KEY (tournament_id)
                                         REFERENCES tournament(tournament_id)
                                         ON DELETE CASCADE
);

CREATE TABLE tournamentgroupmember (
                                       tournament_group_member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       user_id BIGINT NOT NULL,
                                       tournament_group_id BIGINT NOT NULL,
                                       score INT NOT NULL,
                                       CONSTRAINT fk_user
                                           FOREIGN KEY (user_id)
                                               REFERENCES users(user_id),
                                       CONSTRAINT fk_tournament_group
                                           FOREIGN KEY (tournament_group_id)
                                               REFERENCES tournamentgroup(tournament_group_id)
);
CREATE TABLE reward (
                        reward_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        reward_amount BIGINT NOT NULL,
                        is_claimed BOOLEAN NOT NULL,
                        tournament_group_member_id BIGINT UNIQUE NOT NULL,
                        CONSTRAINT fk_tournament_group_member
                            FOREIGN KEY (tournament_group_member_id)
                                REFERENCES tournamentgroupmember(tournament_group_member_id)
);

SET FOREIGN_KEY_CHECKS = 1;
