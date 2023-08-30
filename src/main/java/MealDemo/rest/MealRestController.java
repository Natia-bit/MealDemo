package MealDemo.rest;

import MealDemo.entity.Meals;
import MealDemo.service.MealService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/home")
public class MealRestController {

    //FIELD
    private MealService mealService;

    // CONSTRUCTOR
    public MealRestController(MealService mealService) {
        this.mealService = mealService;
    }


    // VIEW ALL MEALS
    @GetMapping("/meals")
    public List<Meals> findAll(){
        return mealService.findAll();
    }

    // ADD MEALS
    @PostMapping("/meals")
    public Meals addMeal(@RequestBody Meals meal){
        // set ID to 0 in case if an id is passed on JSON
        // This will force to update
        meal.setId(0);

        return mealService.save(meal);
    }

    // UPDATE MEALS (BY ID)
    @PutMapping("/meals")
    public Meals updateMeal(@RequestBody Meals meal){
        return mealService.save(meal);
    }

    // DELETE MEALS (BY ID)
    @DeleteMapping("/meals/{mealId}")
    public String deleteMeal(@PathVariable int mealID){
        Optional tempMeal = mealService.findById(mealID);

        if (tempMeal.isEmpty()){
            throw new RuntimeException("Meal ID not found");
        }

        mealService.deleteByID(mealID);

        return "Meal ID " + mealID + " is deleted";
    }

}
