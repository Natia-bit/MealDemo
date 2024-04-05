package MealDemo.rest;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
