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


    private MealPlanningServiceImpl mealTestDb;

    @Before
    public void setUp() {
        mealTestDb = new MealPlanningServiceImpl(mealRepository, frequencyRepository);
        mealTestDb.saveNewMeal(new Meal("Chicken Tacos", "Chicken"));
        mealTestDb.saveNewMeal(new Meal("Beef Burger", "Beef"));
        mealTestDb.saveNewMeal(new Meal("Fish and Chips", "Fish"));
        mealTestDb.saveNewMeal(new Meal("Black Bean Enchiladas", "Veggies"));
    }

    @Test
    public void whenSaveNewMealChicken_ThenReturnMealChicken(){
        assertEquals(mealTestDb.getMeals().get(0).getMealName(), "Chicken Tacos");
        assertEquals(mealTestDb.getMeals().get(0).getCategory(), "Chicken");
        assertEquals(mealTestDb.getMeals().get(0).getId(), 1);
    }

    @Test
    public void whenSaveNewMealBeef_ThenReturnMealBeef(){
        assertEquals(mealTestDb.getMeals().get(1).getMealName(), "Beef Burger");
        assertEquals(mealTestDb.getMeals().get(1).getCategory(), "Beef");
        assertNotEquals(mealTestDb.getMeals().get(1).getCategory(), "Chicken");
    }

    @Test
    public void whenGivenMealId_ShouldReturnMealWithSameId(){
        // with if statement check that data is present as the method returns Optional<>
        if (mealTestDb.findMealById(1).isPresent()){
            assertEquals(mealTestDb.getMeals().get(0).getMealName(),
                         mealTestDb.findMealById(1).get().getMealName());
        }

        if (mealTestDb.findMealById(2).isPresent()){
            assertEquals(mealTestDb.getMeals().get(1).getMealName(),
                         mealTestDb.findMealById(2).get().getMealName());
        }

        if (mealTestDb.findMealById(1).isPresent() && mealTestDb.findMealById(2).isPresent()){
            assertNotEquals(mealTestDb.findMealById(1).get().getMealName(),
                            mealTestDb.getMeals().get(2).getMealName());
        }
    }

    @Test
    public void whenUpdatingMealName_ShouldReturnNewName(){
        Meal newMealDetails = new Meal("Updated name", "Updated category");

        if (mealTestDb.findMealById(1).isPresent()){
            mealTestDb.updateMeal(1, newMealDetails);
            assertEquals(mealTestDb.getMeals().get(0).getMealName(), "Updated name");
            assertNotEquals(mealTestDb.getMeals().get(0).getMealName(), "Beef Burger");
            assertEquals(mealTestDb.getMeals().size(), 4);
        }


        System.out.println("Verifying: " + mealTestDb.getMeals().get(0).getMealName());
        System.out.println("Verifying: " + mealTestDb.getMeals().get(1).getMealName());


//            assertEquals(mealTestDb.getMeals().get(0).getMealName(), "Updated name");
//            // checks if it updated the right one with the right details
//            assertNotEquals(mealTestDb.getMeals().get(0).getMealName(), "Beef Burger");
//            // check if it didn't modify other data
//            assertEquals(mealTestDb.getMeals().get(1).getMealName(), "Beef Burger");
//            // check it didn't add a new entry
//            Assertions.assertThrows(IndexOutOfBoundsException.class, () -> mealTestDb.getMeals().get(4));

        // checks if it updates
    }


    @Test
    public void whenDeletingMealWithId_ShouldReturnEmpty(){
        mealTestDb.deleteMeal(1);
        assertEquals(mealTestDb.findMealById(1), Optional.empty());

        mealTestDb.deleteMeal(2);
        assertEquals(mealTestDb.findMealById(2), Optional.empty());

        assertEquals(mealTestDb.getMeals().get(0).getMealName(), "Chicken Tacos");
    }
}
