package MealDemo.rest;

import MealDemo.entity.Meal;
import MealDemo.service.MealService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    // F: (nitpick) In reality you wont use a find all.
    // F: In reality you will either paginate or get by user or some filtering shit
    @GetMapping("/meals")
    public List<Meal> findAll(){
        return mealService.findAll();
    }

    // F: No exception really needed. Nothing broke. Just return a 404
    @GetMapping("/meals/{mealId}")
    public Meal findById(@PathVariable int mealId){
        Meal meal = mealService.findById(mealId);

        if (meal == null){
            System.out.println("Meal not found");;
        }
       return meal;
    }

    // ADD MEALS
    // F: Bad bad bad
    // F: You need one POST endpoint to do creations
    // F: Then you need a PUT endpoint to do updates
    // F: Dont combine them this is bad design. It's known as a God Endpoint.
    // F: Given your target design this is useless. You should always save a meal with a frequency and this is breaking that . . . kill it.
    @PostMapping("/meals")
    public Meal addMeal(@RequestBody Meal meal){
        // set ID to 0 in case if an id is passed on JSON
        // This will force to update
        meal.setId(0);

        return mealService.save(meal);
    }



    // DELETE MEALS (BY ID)
    // F: Make sure this returns a 404
    // F: The exception is not a runtime exception. You dont even need to throw an exception. Just make sure you return a 404
    @DeleteMapping("/meals/{mealId}")
    public String deleteMeal(@PathVariable int mealId){
        // F: Move this line into the delete method. You can't delete without finding so they belong together
        // F: Then you can change the return type of the delete to be Boolean and handle the 404 logic based on if the boolean is true or false
        Meal tempMeal = mealService.findById(mealId);

        if (tempMeal == null){
            throw new RuntimeException("Meal ID not found");
        }

        mealService.deleteByID(mealId);

        return "Meal ID " + mealId + " is deleted";
    }


}
