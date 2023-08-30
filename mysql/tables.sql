--DROP TABLES
DROP TABLE IF EXISTS frequency;
DROP TABLE IF EXISTS meals;


-- CREATE TABLES
CREATE TABLE meals (
    id INTEGER NOT NULL AUTO_INCREMENT,
    meal_name VARCHAR(255) NOT NULL,
    category VARCHAR(80) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE frequency (
    id INTEGER NOT NULL AUTO_INCREMENT,
    times_used INTEGER DEFAULT 0,
    meal_id INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(meal_id) REFERENCES meals(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
