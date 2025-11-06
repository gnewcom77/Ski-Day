-- Clean existing rows (child -> parents) without disabling FK checks
DELETE FROM ski_session;
DELETE FROM resort;
DELETE FROM `user`;  -- backticks: USER is reserved in MySQL

-- (Optional) reset auto-increment counters for nicer IDs; OK to omit if you don't care
ALTER TABLE `user` AUTO_INCREMENT = 1;
ALTER TABLE resort AUTO_INCREMENT = 1;
ALTER TABLE ski_session AUTO_INCREMENT = 1;

-- Users
INSERT INTO user (name) VALUES ('Garrett');
INSERT INTO user (name) VALUES ('Kinsey');

-- Resorts
INSERT INTO resort (name, region, state) VALUES ('Bridger Bowl', 'Gallatin County', 'MT');
INSERT INTO resort (name, region, state) VALUES ('Jackson Hole', 'Teton County', 'WY');
INSERT INTO resort (name, region, state) VALUES ('Lost Trail', 'Ravalli County', 'MT');
INSERT INTO resort (name, region, state) VALUES ('Discovery', 'Granite County', 'MT');

-- Ski Sessions
INSERT INTO ski_session (user_id, resort_id, season, date, type, conditions, notes)
VALUES (1, 1, '2024/25', '2025-01-20', 'RESORT', 'powder', 'Fun Day');

INSERT INTO ski_session (user_id, resort_id, season, date, type, conditions, notes)
VALUES (2, 2, '2024/25', '2025-02-05', 'RESORT', 'packed powder', 'Skied Out.');

INSERT INTO ski_session (user_id, season, date, type, conditions, notes)
VALUES (1, '2024/25', '2025-02-12', 'BACKCOUNTRY', 'Fresh 6', 'Lolo Pass.');