package MealDemo.service;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meals;

import java.util.List;

public interface IMealPlanningService {

    void seeAllMeals();
    List<Meals> viewAllMeals();
    List<Frequency> viewAllFreq();

    Frequency findByTheId(int id);

    Meals addNewMeal(Meals meal);





}
