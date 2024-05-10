package MealDemo.config;

import MealDemo.entity.Meal;
import MealDemo.service.MealPlanningServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final MealPlanningServiceImpl mealPlanningServiceImpl;


    public DataLoader(MealPlanningServiceImpl mealPlanningServiceImpl) {
        this.mealPlanningServiceImpl = mealPlanningServiceImpl;
    }

    @Override
    public void run(String... args) throws Exception {
        mealPlanningServiceImpl.saveNewMeal(new Meal("Chicken pie", "Chicken"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Chicken ozro", "Chicken"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Creamy Tuscan chicken", "Chicken"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Chicken fried rice", "Chicken"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Dijon Chicken", "Chicken"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Chicken with pesto and parmaham", "Chicken"));

        mealPlanningServiceImpl.saveNewMeal(new Meal("Beef burger with sweet potato fries", "Meat"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Chili con carne", "Meat"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Chickpea chorizo casserole", "Meat"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Steak with creamy spinach", "Meat"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Beef stew", "Meat"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Tacos", "Meat"));

        mealPlanningServiceImpl.saveNewMeal(new Meal("Fish and chips", "Fish"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Tuna salad", "Fish"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Sushi", "Fish"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Smoky chorizo salmon", "Fish"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Upside down fish pie", "Fish"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Sardine spaghetti", "Fish"));

        mealPlanningServiceImpl.saveNewMeal(new Meal("Falafel with rice", "Vegetarian"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Gnocchi & tomato bake", "Vegetarian"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Bean enchiladas", "Vegetarian"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Buddy’s smoky beans with baked potato", "Vegetarian"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Mushroom quesadillas", "Vegetarian"));
        mealPlanningServiceImpl.saveNewMeal(new Meal("Burnt aubergine veggie chilli", "Vegetarian"));



    }
}
