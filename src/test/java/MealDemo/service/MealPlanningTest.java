package MealDemo.service;

import MealDemo.dao.FrequencyRepository;
import MealDemo.dao.MealRepository;
import MealDemo.entity.Meal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MealPlanningTest {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FrequencyRepository frequencyRepository;


    private MealPlanningServiceImpl mealsTestData;

    @Before
    public void setUp() {
        mealsTestData = new MealPlanningServiceImpl(mealRepository, frequencyRepository);
        mealsTestData.saveNewMeal(new Meal("Chicken Tacos", "Chicken"));
        mealsTestData.saveNewMeal(new Meal("Beef Burger", "Beef"));
        mealsTestData.saveNewMeal(new Meal("Fish and Chips", "Fish"));
        mealsTestData.saveNewMeal(new Meal("Black Bean Enchiladas", "Veggies"));
    }

    @Test
    public void whenSaveNewMealChicken_ThenReturnMealChicken(){
        assertEquals(mealsTestData.getMeals().get(0).getMealName(), "Chicken Tacos");
        assertEquals(mealsTestData.getMeals().get(0).getCategory(), "Chicken");
        assertEquals(mealsTestData.getMeals().get(0).getId(), 1);
    }

    @Test
    public void whenSaveNewMealBeef_ThenReturnMealBeef(){
        assertEquals(mealsTestData.getMeals().get(1).getMealName(), "Beef Burger");
        assertEquals(mealsTestData.getMeals().get(1).getCategory(), "Beef");
        assertNotEquals(mealsTestData.getMeals().get(1).getCategory(), "Chicken");
    }

    @Test
    public void whenGivenMealId_ShouldReturnMealWithSameId(){
        // with if statement check that data is present as the method returns Optional<>
        if (mealsTestData.findMealById(1).isPresent()){
            assertEquals(mealsTestData.getMeals().get(0).getMealName(),
                         mealsTestData.findMealById(1).get().getMealName());
        }

        if (mealsTestData.findMealById(2).isPresent()){
            assertEquals(mealsTestData.getMeals().get(1).getMealName(),
                         mealsTestData.findMealById(2).get().getMealName());
        }

        if (mealsTestData.findMealById(1).isPresent() && mealsTestData.findMealById(2).isPresent()){
            assertNotEquals(mealsTestData.findMealById(1).get().getMealName(),
                            mealsTestData.getMeals().get(2).getMealName());
        }
    }

    @Test
    public void whenUpdatingMealName_ShouldReturnNewName(){
        Meal newMealDetails = new Meal("Updated name", "Updated category");

        if (mealsTestData.findMealById(1).isPresent()){
            mealsTestData.updateMeal(1, newMealDetails);
            assertEquals(mealsTestData.getMeals().get(0).getMealName(), "Updated name");
            assertNotEquals(mealsTestData.getMeals().get(0).getMealName(), "Beef Burger");
            assertEquals(mealsTestData.getMeals().size(), 4);
        }
    }


    @Test
    public void whenDeletingMealWithId_ShouldReturnEmpty(){
        mealsTestData.deleteMeal(1);
        assertEquals(mealsTestData.findMealById(1), Optional.empty());

        mealsTestData.deleteMeal(2);
        assertEquals(mealsTestData.findMealById(2), Optional.empty());

        assertEquals(mealsTestData.getMeals().get(0).getMealName(), "Chicken Tacos");
    }
}
