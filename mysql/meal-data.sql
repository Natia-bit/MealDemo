-- RAW DATA : MEALS Chicken
INSERT INTO meals (meal_name, category)
    VALUES
    ('Chicken pie', 'Chicken'),
    ('Steamed chicken', 'Chicken'),
    ('Chicken ozro', 'Chicken'),
    ('Creamy Tuscan chicken', 'Chicken'),
    ('Chicken fried rice', 'Chicken'),
    ('Chicken with pesto and parmaham', 'Chicken'),
    ('Dijon Chicken', 'Chicken');

-- RAW DATA : MEALS Meat
INSERT INTO meals (meal_name, category)
    VALUES
    ('Beef burger with sweet potato fries', 'Meat'),
    ('Chili con carne', 'Meat'),
    ('Chickpea chorizo casserole', 'Meat'),
    ('Steak and buckwheat', 'Meat');

-- RAW DATA : MEALS Fish
INSERT INTO meals (meal_name, category)
    VALUES
    ('Fish bake', 'Fish'),
    ('Tuna salad', 'Fish'),
    ('Smoky chorizo salmon', 'Fish');

-- RAW DATA : MEALS Vegetarian
INSERT INTO meals (meal_name, category)
    VALUES
    ('Vegetarian quesadilla', 'Vegetarian'),
    ('Falafel with rice', 'Vegetarian'),
    ('Gnocchi & tomato bake', 'Vegetarian'),
    ('Bean enchiladas', 'Vegetarian');
       
-- RAW DATA: FREQUENCY
INSERT INTO frequency (meal_id) SELECT id FROM meals; 