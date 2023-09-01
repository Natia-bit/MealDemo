package MealDemo.dao;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meals;

public interface AppDAO {

    void save(Meals meal);
    Frequency findFrequencyByMeal(int mealId);
    void deleteMealById(int id);









}
