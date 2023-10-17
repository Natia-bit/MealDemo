package MealDemo.service;

import MealDemo.dao.FrequencyRepository;
import MealDemo.dao.MealRepository;
import MealDemo.entity.Meal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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

//    @Before
//    public void setUp(){
//        MealPlanningServiceImpl mealsDb = new MealPlanningServiceImpl(mealRepository, frequencyRepository);
//        mealsDb.saveNewMeal(new Meal("Beef burritos", "Beef"));
//        mealsDb.saveNewMeal(new Meal("Chicken enchiladas", "Chicken"));
//        mealsDb.saveNewMeal(new Meal("Fish Tacos", "Fish"));
//    }


    @Test
    public void whenGivenMealId_ShouldReturnMealWithSameId(){
        MealPlanningServiceImpl mealsDb = new MealPlanningServiceImpl(mealRepository, frequencyRepository);
        Meal meal = new Meal("Beef burritos", "Beef");
        mealsDb.saveNewMeal(meal);

        mealsDb.saveNewMeal(new Meal("Chicken enchiladas", "Chicken"));
        mealsDb.saveNewMeal(new Meal("Fish Tacos", "Fish"));

        // actual is 1, and always will be +1 from index, as the db count starts from 1 not 0
        assertEquals(mealsDb.getMeals().get(0).getId(), 1);
        assertEquals(mealsDb.getMeals().get(1).getId(), 2);
        assertEquals(mealsDb.getMeals().get(2).getId(), 3);
    }





    @Test
    public void testAdd() {
        String str = "Junit is working fine";
        assertEquals("Junit is working fine",str);
    }

    @Test
    public void test() {
        assertEquals("Hello world","Hello world");
    }

}
