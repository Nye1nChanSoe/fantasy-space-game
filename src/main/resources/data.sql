INSERT INTO account (name, username, password)
SELECT 'Game', 'game', 'game'
    WHERE NOT EXISTS (SELECT 1 FROM account WHERE username = 'game');

INSERT INTO account (name, username, password)
SELECT 'Motyka', 'motyka', 'heslo'
    WHERE NOT EXISTS (SELECT 1 FROM account WHERE username = 'motyka');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Moni The Teacher', 100, 40, 30, 30, 6000, 10, 'SORCERER'
FROM account WHERE username = 'motyka'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Moni The Teacher');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Harry Potter', 100, 40, 30, 30, 0, 1, 'SORCERER'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Harry Potter');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Hermione Granger', 90, 40, 40, 30, 0, 1, 'SORCERER'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Hermione Granger');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Ron Weasley', 120, 50, 20, 10, 0, 1, 'SORCERER'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Ron Weasley');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Severus Snape', 80, 60, 30, 30, 0, 1, 'SORCERER'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Severus Snape');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Albus Dumbledore', 90, 40, 40, 30, 0, 1, 'SORCERER'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Albus Dumbledore');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Lord Voldemort', 80, 80, 10, 30, 0, 1, 'SORCERER'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Lord Voldemort');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Minerva McGonagall', 100, 40, 30, 30, 0, 1, 'SORCERER'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Minerva McGonagall');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Bellatrix Lestrange', 80, 70, 20, 30, 0, 1, 'SORCERER'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Bellatrix Lestrange');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Draco Malfoy', 100, 40, 30, 30, 0, 1, 'SORCERER'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Draco Malfoy');

INSERT INTO character (account_id, name, health, attack, mana, healing, experience, level, class)
SELECT id, 'Neville Longbottom', 130, 30, 10, 30, 0, 1, 'SORCERER'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Neville Longbottom');

INSERT INTO character (account_id, name, health, attack, stamina, defense, experience, level, class)
SELECT id, 'Luke Skywalker', 110, 40, 20, 30, 0, 1, 'WARRIOR'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Luke Skywalker');

INSERT INTO character (account_id, name, health, attack, stamina, defense, experience, level, class)
SELECT id, 'Yoda', 80, 30, 50, 40, 0, 1, 'WARRIOR'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Yoda');

INSERT INTO character (account_id, name, health, attack, stamina, defense, experience, level, class)
SELECT id, 'Han Solo', 120, 40, 10, 30, 0, 1, 'WARRIOR'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Han Solo');

INSERT INTO character (account_id, name, health, attack, stamina, defense, experience, level, class)
SELECT id, 'Darth Vader', 100, 60, 10, 30, 0, 1, 'WARRIOR'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Darth Vader');

INSERT INTO character (account_id, name, health, attack, stamina, defense, experience, level, class)
SELECT id, 'Obi-Wan Kenobi', 100, 40, 30, 30, 0, 1, 'WARRIOR'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Obi-Wan Kenobi');

INSERT INTO character (account_id, name, health, attack, stamina, defense, experience, level, class)
SELECT id, 'Emperor Palpatine', 80, 80, 10, 30, 0, 1, 'WARRIOR'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Emperor Palpatine');

INSERT INTO character (account_id, name, health, attack, stamina, defense, experience, level, class)
SELECT id, 'Mace Windu', 110, 40, 20, 30, 0, 1, 'WARRIOR'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Mace Windu');

INSERT INTO character (account_id, name, health, attack, stamina, defense, experience, level, class)
SELECT id, 'Darth Maul', 90, 60, 20, 30, 0, 1, 'WARRIOR'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Darth Maul');

INSERT INTO character (account_id, name, health, attack, stamina, defense, experience, level, class)
SELECT id, 'Kylo Ren', 100, 50, 20, 30, 0, 1, 'WARRIOR'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Kylo Ren');

INSERT INTO character (account_id, name, health, attack, stamina, defense, experience, level, class)
SELECT id, 'Finn', 130, 20, 10, 40, 0, 1, 'WARRIOR'
FROM account WHERE username = 'game'
               AND NOT EXISTS (SELECT 1 FROM character WHERE name = 'Finn');
