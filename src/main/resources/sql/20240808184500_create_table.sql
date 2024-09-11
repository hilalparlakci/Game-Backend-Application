CREATE TABLE country (
    name VARCHAR(255) PRIMARY KEY
);

INSERT INTO country (name) VALUES ('Turkey');
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
                                       user_id BIGINT,
                                       tournament_group_id BIGINT,
                                       score INT NOT NULL,
                                       reward_id BIGINT,
                                       CONSTRAINT fk_tournament_group
                                           FOREIGN KEY (tournament_group_id)
                                               REFERENCES tournamentgroup(tournament_group_id)
                                               ON DELETE CASCADE,
                                       CONSTRAINT fk_user
                                           FOREIGN KEY (user_id)
                                               REFERENCES users(user_id)
);
CREATE TABLE reward (
                        reward_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        reward_amount BIGINT,
                        is_claimed BOOLEAN,
                        tournament_group_member_id BIGINT,
                        CONSTRAINT fk_tournament_group_member
                            FOREIGN KEY (tournament_group_member_id)
                                REFERENCES tournamentgroupmember(tournament_group_member_id)
);

ALTER TABLE tournamentgroupmember
    ADD CONSTRAINT fk_reward
        FOREIGN KEY (reward_id)
            REFERENCES reward(reward_id);

INSERT INTO tournament (tournament_date)
VALUES (CURDATE());

UPDATE users
SET user_level = 21
WHERE user_id = 3;

UPDATE users
SET user_level = 21
WHERE user_id = 1;

UPDATE users
SET user_level = 21
WHERE user_id = 2;

SET FOREIGN_KEY_CHECKS = 0;

