package MealDemo.service;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;

import java.util.List;

public interface MealPlanningService {

    List<Meal> getMeals();
    List<Frequency> getFrequencies();

    Meal findMealById(int mealId);

    void saveNewMeal(Meal meal);

    void updateMeal(int mealId, Meal meal);

    boolean deleteMeal(int id);


}
