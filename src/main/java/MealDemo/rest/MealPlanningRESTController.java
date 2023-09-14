package MealDemo.rest;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;
import MealDemo.service.MealPlanningServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Meal findByMealById(@PathVariable int mealId){
        Meal tempMeal = mealPlanningServiceImpl.findMealById(mealId);

        return tempMeal ;
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
        mealPlanningServiceImpl.updateMeal(mealId, theMeal);
    }


}

