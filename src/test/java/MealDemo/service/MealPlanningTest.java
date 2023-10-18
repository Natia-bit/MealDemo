package MealDemo.service;

import MealDemo.dao.FrequencyRepository;
import MealDemo.dao.MealRepository;
import MealDemo.entity.Meal;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
        MealPlanningServiceImpl mealsDb1 = new MealPlanningServiceImpl(mealRepository, frequencyRepository);
        mealsDb1.saveNewMeal(meal);

        assertEquals(mealsDb1.getMeals().get(0), meal);
        assertEquals(mealsDb1.getMeals().get(0).getMealName(), "Beef Burger");
        assertEquals(mealsDb1.getMeals().get(0).getCategory(), "Beef");
        assertNotEquals(mealsDb1.getMeals().get(0).getCategory(), "Chicken");
    }

    @Test
    public void whenGivenMealId_ShouldReturnMealWithSameId(){
        MealPlanningServiceImpl mealsDb = new MealPlanningServiceImpl(mealRepository, frequencyRepository);

        // Case 1
        Meal meal1 = new Meal("Beef burritos", "Beef");
        mealsDb.saveNewMeal(meal1);

        if (mealsDb.findMealById(1).isPresent()){
            System.out.println(mealsDb.findMealById(1).get().getMealName());
            System.out.println(meal1.getMealName());
            assertEquals(mealsDb.findMealById(1).get().getMealName(), meal1.getMealName());
        }

        // Case 2
        Meal meal2 = new Meal("Chicken Pasta Bake", "Chicken");
        mealsDb.saveNewMeal(meal2);

        if (mealsDb.findMealById(2).isPresent()){
            System.out.println(mealsDb.findMealById(2).get().getMealName());
            System.out.println(meal2.getMealName());
            assertEquals(mealsDb.findMealById(2).get().getMealName(), meal2.getMealName());
        }

        // Case 4
        System.out.println("Case 4");
        if (mealsDb.findMealById(1).isPresent() && mealsDb.findMealById(2).isPresent()){
            assertNotEquals(mealsDb.findMealById(1).get().getMealName(), meal2.getMealName());
        }
        System.out.println("Complete");

    }

    @Test
    public void whenUpdatingMealName_ShouldReturnNewName(){

        MealPlanningServiceImpl mealTestDB = new MealPlanningServiceImpl(mealRepository, frequencyRepository);
        mealTestDB.saveNewMeal(new Meal("Chicken Test", "Chicken"));
        mealTestDB.saveNewMeal(new Meal("Beef Test", "Beef"));

        Meal updatedMeal = new Meal("Updated name", "Updated category");
        mealTestDB.updateMeal(1, updatedMeal);

         // checks if it updates
        assertEquals(mealTestDB.getMeals().get(0).getMealName(), "Updated name");
        // checks if it updated the right one with the right details
        assertNotEquals(mealTestDB.getMeals().get(0).getMealName(), "Chicken Test");
        // check if it didn't modify other data
        assertEquals(mealTestDB.getMeals().get(1).getMealName(), "Beef Test");
        // check it didn't add a new entry
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> mealTestDB.getMeals().get(2) );
    }




}
