package MealDemo.rest;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;
import MealDemo.service.DaysOfTheWeek;
import MealDemo.service.MealPlanningServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/home")
public class MealPlanningRESTController {

    private MealPlanningServiceImpl mealPlanningServiceImpl;

    public MealPlanningRESTController(MealPlanningServiceImpl mealPlanningServiceImpl) {
        this.mealPlanningServiceImpl = mealPlanningServiceImpl;
    }


    @GetMapping("/meals")
    public List<Meal> viewAllMeals(){
        return mealPlanningServiceImpl.getMeals();
    }


    @GetMapping("meals/frequencies")
    public List<Frequency> viewAllFreq(){
        return mealPlanningServiceImpl.getFrequencies();
    }


    @GetMapping("/meals/{mealId}")
    public Meal findByMealId(@PathVariable int mealId){
        Optional<Meal> tempMeal = mealPlanningServiceImpl.findMealById(mealId);

        Meal meal = null;

        if (tempMeal.isPresent()){
            meal = tempMeal.get();
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Invalid meal ID");
        }

        return meal ;
    }


    @PostMapping("/meals")
    public void addMealsWithFreq(@RequestBody Meal meal){
         mealPlanningServiceImpl.saveNewMeal(meal);
    }


    @DeleteMapping("/meals/{mealId}")
    public void deleteMeal (@PathVariable int mealId){
        mealPlanningServiceImpl.deleteMeal(mealId);
    }

    @PutMapping("/meals/{mealId}")
    public void updateMeal(@RequestBody Meal theMeal, @PathVariable int mealId){
        Optional<Meal> tempMeal = mealPlanningServiceImpl.findMealById(mealId);

        if (tempMeal.isPresent()){
            mealPlanningServiceImpl.updateMeal(mealId, theMeal);
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Invalid meal ID");
        }
    }

    @GetMapping("/meals/weekly-menu")
    public HashMap<DaysOfTheWeek, Meal> generateWeeklyMeals(){
        return mealPlanningServiceImpl.generateWeeklyMeals();
    }

    @GetMapping("/meals/testing")
    public String testing(){
        return mealPlanningServiceImpl.test();
    }

}

