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
        mealPlanningService.getAllMeals();
    }

    // WORKING :)
    @GetMapping("/viewM")
    public List<Meals> viewAllMeals(){
        return mealPlanningService.getMeals();
    }


    @GetMapping("/viewF")
    public List<Frequency> viewAllFreq(){
        return mealPlanningService.getFrequencies();
    }

    @GetMapping("/meals/{mealId}")
    public Frequency findByMealID(@PathVariable int mealId){
        Frequency frequency = mealPlanningService.findFreqByTheId(mealId);

        if (frequency == null){
            throw new RuntimeException("Meal ID not found");
        }
        return frequency;
    }


    @PostMapping("/meal")
    public Meals addMealsWithFreq(@RequestBody Meals meal){
        return mealPlanningService.saveNewMeal(meal);
    }


    @DeleteMapping("/meal/{id}")
    public String deleteMeal (@PathVariable int id){
        Frequency tempMeal = mealPlanningService.findFreqByTheId(id);

        if (tempMeal == null){
            throw new RuntimeException(("Meal ID not found"));
        }

        mealPlanningService.deleteMeal(id);

        return "Meal ID " +tempMeal.getClass().getName() +  id + " is deleted";
    }

    @PutMapping("/meals/{mealId}")
    public void updateMeal(@RequestBody Meals theMeal, @PathVariable int mealId){
        mealPlanningService.updateMeal(mealId, theMeal);

//        Meals dbMeal = mealPlanningService.saveNewMeal(theMeal);

//        return dbMeal;
    }

    }

