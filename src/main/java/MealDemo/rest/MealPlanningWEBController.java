package MealDemo.rest;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("")
public class MealPlanningWEBController {

    @GetMapping("/")
    public String viewHomePage(Model theModel){
        return "index.html";
    }

    @GetMapping("/meals")
    public String viewMealPage(Model theModel){
        return "meals.html";
    }

    @PostMapping("/meals")
    public String addMealForm(Model theModel){
        return "meals.html";
    }



    @DeleteMapping("/meals")
    public String deleteMeal(int mealId){
        return "meals.html";
    }

    @PostMapping("/weekly-plan")
    public String viewWeeklyMenu(Model theModel){
        return "weekly-plan.html";
    }

}
