package MealDemo.service;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meals;

import java.util.List;

public interface IMealPlanningService {

    // VIEW ALL
    void getAllMeals();

    // VIEW ENTITIES
    List<Meals> getMeals();
    List<Frequency> getFrequencies();


    // FIND BY ID
    Meals findMealById(int id);
    Frequency findFreqByTheId(int id);


    // SAVE NEW (OR UPDATE)
    Meals saveNewMeal(Meals meal);

    // DELETE
    void deleteMeal(int id);


    //UPDATE
    void updateMeal(int mealId, Meals meal);







}
