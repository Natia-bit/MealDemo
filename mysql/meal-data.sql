-- RAW DATA : MEALS
INSERT INTO meals (meal_name, category)
    VALUES
    ('Chicken pie', 'Chicken'),
    ('Steamed chicken', 'Chicken'),
    ('Chicken ozro', 'Chicken'),
    ('Creamy Tuscan chicken', 'Chicken'),
    ('Chicken fried rice', 'Chicken'),
    ('Chicken with pesto and parmeham', 'Chicken'),
    ('Dijon Chicken', 'Chicken');
       
-- RAW DATA: FREQUENCY
INSERT INTO frequency (meal_id) SELECT id FROM meals; 