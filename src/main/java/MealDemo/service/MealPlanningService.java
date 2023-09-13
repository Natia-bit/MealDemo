package MealDemo.service;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;

import java.util.List;
import java.util.Optional;

public interface MealPlanningService {

    // VIEW ENTITIES
    List<Meal> getMeals();
    List<Frequency> getFrequencies();

    // FIND BY ID
    Optional<Meal> findMealById(int mealId);


    // SAVE NEW
    void saveNewMeal(Meal meal);


    //UPDATE
    void updateMeal(int mealId, Meal meal);


    // DELETE
    boolean deleteMeal(int id);


}
