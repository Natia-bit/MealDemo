package MealDemo.rest;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;
import MealDemo.service.DaysOfTheWeek;
import MealDemo.service.MealPlanningServiceImpl;
import ch.qos.logback.core.model.Model;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

//@RestController
@Controller
@RequestMapping("")
public class MealPlanningRESTController {

    private MealPlanningServiceImpl mealPlanningServiceImpl;

    public MealPlanningRESTController(MealPlanningServiceImpl mealPlanningServiceImpl) {
        this.mealPlanningServiceImpl = mealPlanningServiceImpl;
    }

    @GetMapping("/home")
    public String viewHomePage(Model theModel){
        return "index.html";
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


    @GetMapping("/meals/categories")
    public Map<String, List<Meal>> getMealsByCategories(){
        return mealPlanningServiceImpl.mealsByCategories();
    }

    @GetMapping("/meals/weekly-menu")
    public Map<DaysOfTheWeek, Meal> generateWeeklyMeals(@RequestBody HashMap<String, Integer> userInput){

        if (!userInput.isEmpty()){
            System.out.println("Using custom values");
            int sumRequest = 0;
            for (int requestNumber : userInput.values()){
                sumRequest += requestNumber;
            }

            if (sumRequest != 7){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request: incorrect number of meals requested. You should request 7 meals. One for each day of the week.");
            }

        } else {
            System.out.println("Using default values");
            userInput.put("Chicken", 2);
            userInput.put("Fish", 1);
            userInput.put("Meat", 2);
            userInput.put("Vegetarian", 2);
        }

        return mealPlanningServiceImpl.generateWeeklyMeals(userInput);
    }

}












