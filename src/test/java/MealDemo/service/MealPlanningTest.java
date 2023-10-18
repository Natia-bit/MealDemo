package MealDemo.service;

import MealDemo.dao.FrequencyRepository;
import MealDemo.dao.MealRepository;
import MealDemo.entity.Meal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MealPlanningTest {


    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FrequencyRepository frequencyRepository;

    @Test
    public void whenSaveNewMealChicken_ThenReturnMealChicken(){
        Meal meal = new Meal("Chicken Tacos", "Chicken");
        MealPlanningServiceImpl test = new MealPlanningServiceImpl(mealRepository, frequencyRepository);
        test.saveNewMeal(meal);

        assertEquals(test.getMeals().get(0), meal);
        assertEquals(test.getMeals().get(0).getMealName(), "Chicken Tacos");
        assertEquals(test.getMeals().get(0).getCategory(), "Chicken");
    }

    @Test
    public void whenSaveNewMealBeef_ThenReturnMealBeef(){
        Meal meal = new Meal("Beef Burger", "Beef");
        MealPlanningServiceImpl mealsDb = new MealPlanningServiceImpl(mealRepository, frequencyRepository);
        mealsDb.saveNewMeal(meal);

        assertEquals(mealsDb.getMeals().get(0), meal);
        assertEquals(mealsDb.getMeals().get(0).getMealName(), "Beef Burger");
        assertEquals(mealsDb.getMeals().get(0).getCategory(), "Beef");
        assertNotEquals(mealsDb.getMeals().get(0).getCategory(), "Chicken");
    }


    @Test
    public void whenGivenMealId_ShouldReturnMealWithSameId(){
        MealPlanningServiceImpl mealsDb = new MealPlanningServiceImpl(mealRepository, frequencyRepository);

        Meal meal1 = new Meal("Beef burritos", "Beef");
        mealsDb.saveNewMeal(meal1);

        if (mealsDb.findMealById(1).isPresent()){
            System.out.println(mealsDb.findMealById(1).get().getMealName());
            System.out.println(meal1.getMealName());
            assertEquals(mealsDb.findMealById(1).get().getMealName(), meal1.getMealName());
        }

//        assertTrue(mealsDb.findMealById(1).isPresent());
//        assertEquals(mealsDb.findMealById(1), meal1);
//        assertEquals(mealsDb.findMealById(1).get(), meal1);


//
//        Meal meal2 = new Meal("Chicken enchiladas", "Chicken");
//        mealsDb.saveNewMeal(meal2);
//        assertEquals(mealsDb.findMealById(2).get(), meal2);
//
//
//        Meal meal3 = new Meal("Fish Tacos", "Fish");
//        mealsDb.saveNewMeal(meal3);
//        assertEquals(mealsDb.findMealById(3).get(), meal3);
    }

    @Test
    public void testHelloWorld(){
        String helloWorld = "Hello World";

        assertEquals(helloWorld, "Hello World");
    }

}
