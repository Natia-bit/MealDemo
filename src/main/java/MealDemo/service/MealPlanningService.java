package MealDemo.service;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MealPlanningService {

    List<Meal> getMeals();
    List<Frequency> getFrequencies();

    Optional<Meal> findMealById(int mealId);

    void saveNewMeal(Meal meal);

    void updateMeal(int mealId, Meal meal);

    boolean deleteMeal(int id);

    Map<String, List<Meal>> mealsByCategories();

    HashMap<DaysOfTheWeek, Meal> generateWeeklyMeals() ;

    String test();




}
