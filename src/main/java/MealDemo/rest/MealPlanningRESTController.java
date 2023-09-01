package MealDemo.rest;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meals;
import MealDemo.service.MealPlanningService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class MealPlanningRESTController {

    private MealPlanningService mealPlanningService;

    public MealPlanningRESTController(MealPlanningService mealPlanningService) {
        this.mealPlanningService = mealPlanningService;
    }


    @GetMapping("/viewAll")
    public void viewAll(){
        mealPlanningService.seeAllMeals();
    }

    // WORKING :)
    @GetMapping("/viewM")
    public List<Meals> viewAllMeals(){
        return mealPlanningService.viewAllMeals();
    }


    @GetMapping("/viewF")
    public List<Frequency> viewAllFreq(){
        return mealPlanningService.viewAllFreq();
    }

    @GetMapping("/meals/{mealId}")
    public Frequency findByMealID(@PathVariable int mealId){
        Frequency frequency = mealPlanningService.findByTheId(mealId);

        if (frequency == null){
            throw new RuntimeException("Meal ID not found");
        }
        return frequency;
    }


    @PostMapping("/meal")
    public Meals addMealsWithFreq(@RequestBody Meals meal){
        return mealPlanningService.addNewMeal(meal);
    }

}
