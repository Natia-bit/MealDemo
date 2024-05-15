package MealDemo.rest;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;
import MealDemo.service.MealPlanningServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("")
public class MealPlanningRestController {

    private MealPlanningServiceImpl mealPlanningServiceImpl;

    public MealPlanningRestController(MealPlanningServiceImpl mealPlanningServiceImpl) {
        this.mealPlanningServiceImpl = mealPlanningServiceImpl;
    }


    @GetMapping("/api/meals")
    public List<Meal> viewAllMeals(){
        return mealPlanningServiceImpl.getMeals();
    }

    @GetMapping("/api/meals/frequencies")
    public List<Frequency> viewAllFreq(){
        return mealPlanningServiceImpl.getFrequencies();
    }


    @GetMapping("/api/meals/{mealId}")
    public Optional<Meal> findByMealId(@PathVariable int mealId){
        return mealPlanningServiceImpl.findMealById(mealId);
    }


    @PostMapping("/api/meals")
    public void addMealsWithFreq(@RequestBody Meal meal){
         mealPlanningServiceImpl.saveNewMeal(meal);
    }


    @DeleteMapping("/api/meals/{mealId}")
    public void deleteMeal (@PathVariable int mealId){
        mealPlanningServiceImpl.deleteMeal(mealId);
    }


    @PutMapping("/api/meals/{mealId}")
    public void updateMeal(@RequestBody Meal theMeal, @PathVariable int mealId){
        mealPlanningServiceImpl.updateMeal(mealId, theMeal);
    }

    @GetMapping("/api/meals/categories")
    public Map<String, List<Meal>> getMealsByCategories(){
        return mealPlanningServiceImpl.mealsByCategories();
    }

    @PostMapping("/api/weekly-plan")
    public Map<DayOfWeek, Meal> generateWeeklyMeals(@RequestBody HashMap<String, Integer> userInput){
        return mealPlanningServiceImpl.generateWeeklyMeals(userInput);
    }

    @GetMapping("/api/meal-random")
    public Meal getRandomMeal(){
        return mealPlanningServiceImpl.randomMeal();
    }

}












