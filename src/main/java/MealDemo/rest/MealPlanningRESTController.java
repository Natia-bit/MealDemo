package MealDemo.rest;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;
import MealDemo.service.MealPlanningServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/test")
public class MealPlanningRESTController {

    private MealPlanningServiceImpl mealPlanningServiceImpl;

    public MealPlanningRESTController(MealPlanningServiceImpl mealPlanningServiceImpl) {
        this.mealPlanningServiceImpl = mealPlanningServiceImpl;
    }



    @GetMapping("/viewM")
    public List<Meal> viewAllMeals(){
        return mealPlanningServiceImpl.getMeals();
    }


    @GetMapping("/viewF")
    public List<Frequency> viewAllFreq(){
        return mealPlanningServiceImpl.getFrequencies();
    }




    @GetMapping("/meals/{mealId}")
    public Optional<Meal> findByMealById(@PathVariable int mealId){
        Optional<Meal> tempMeal = mealPlanningServiceImpl.findMealById(mealId);

        return tempMeal ;
    }


    @PostMapping("/meal")
    public void addMealsWithFreq(@RequestBody Meal meal){
         mealPlanningServiceImpl.saveNewMeal(meal);
    }



    // Deletes based on freq id not meal id
    // ** change to meal id
    @DeleteMapping("/meal/{mealId}")
    public void deleteMeal (@PathVariable int mealId){
        mealPlanningServiceImpl.deleteMeal(mealId);
    }

    @PutMapping("/meals/{mealId}")
    public void updateMeal(@RequestBody Meal theMeal, @PathVariable int mealId){
        mealPlanningServiceImpl.updateMeal(mealId, theMeal);
    }


}

