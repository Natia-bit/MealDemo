package MealDemo.rest;

import MealDemo.entity.Meals;
import MealDemo.service.FrequencyService;
import MealDemo.service.MealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class MealFreqRestController {
    // Fields
    private MealService mealService;
    private FrequencyService frequencyService;

    public MealFreqRestController(MealService mealService, FrequencyService frequencyService) {
        this.mealService = mealService;
        this.frequencyService = frequencyService;
    }

    @GetMapping("/test1")
    public void mealAndFrequency(Meals meals){
        meals.getMealName();
        meals.getCategory();
//        meals.getFrequency().getTimesUsed();
    }

}
