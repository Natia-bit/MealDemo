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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


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
        mealsTestData.saveNewMeal(new Meal("Chicken pie", "Chicken"));
        mealsTestData.saveNewMeal(new Meal("Beef Burger", "Meat"));
        mealsTestData.saveNewMeal(new Meal("Fish bake", "Fish"));
        mealsTestData.saveNewMeal(new Meal("Bean Enchiladas", "Vegetarian"));

        mealsTestData.saveNewMeal(new Meal("Chicken ozro", "Chicken"));
        mealsTestData.saveNewMeal(new Meal("Chili con carne", "Meat"));
        mealsTestData.saveNewMeal(new Meal("Tuna salad", "Fish"));
        mealsTestData.saveNewMeal(new Meal("Falafel with rice", "Vegetarian"));

        mealsTestData.saveNewMeal(new Meal("Creamy Tuscan chicken", "Chicken"));
        mealsTestData.saveNewMeal(new Meal("Steak and buckwheat", "Meat"));
        mealsTestData.saveNewMeal(new Meal("Smoky chorizo salmon", "Fish"));
        mealsTestData.saveNewMeal(new Meal("Gnocchi & tomato bake", "Vegetarian"));

    }

    @Test
    public void whenSaveNewMealChicken_ThenReturnMealChicken(){
        assertEquals(mealsTestData.getMeals().get(0).getMealName(), "Chicken pie");
        assertEquals(mealsTestData.getMeals().get(0).getCategory(), "Chicken");
        assertEquals(mealsTestData.getMeals().get(0).getId(), 1);
    }

    @Test
    public void whenSaveNewMealBeef_ThenReturnMealBeef(){
        assertEquals(mealsTestData.getMeals().get(1).getMealName(), "Beef Burger");
        assertEquals(mealsTestData.getMeals().get(1).getCategory(), "Meat");
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

        assertEquals(mealsTestData.getMeals().get(0).getMealName(), "Chicken pie");
    }


    @Test
    public void whenCallingMealsByCategoryChicken_ShouldReturnSameCategoryChicken(){
        Map<String, List<Meal>> mealsByCategories = mealsTestData.mealsByCategories();
        assertTrue(mealsByCategories.containsKey("Chicken"));
    }

    @Test
    public void whenCallingMealsByCategoryMeat_ShouldReturnSameCategoryMeat(){
        Map<String, List<Meal>> mealsByCategories = mealsTestData.mealsByCategories();
        assertTrue(mealsByCategories.containsKey("Meat"));
    }

    @Test
    public void whenCallingMealsByCategoryFish_ShouldReturnSameCategoryFish(){
        Map<String, List<Meal>> mealsByCategories = mealsTestData.mealsByCategories();
        assertTrue(mealsByCategories.containsKey("Fish"));
    }

    @Test
    public void whenCallingMealsByCategoryVegetarian_ShouldReturnSameCategoryVegetarian(){
        Map<String, List<Meal>> mealsByCategories = mealsTestData.mealsByCategories();
        assertTrue(mealsByCategories.containsKey("Vegetarian"));
    }


    @Test
    public void whenCallingMealsByCategoriesUnknown_ShouldReturnSameCategories(){
        Map<String, List<Meal>> mealsByCategories = mealsTestData.mealsByCategories();
        String unknownKey = mealsByCategories.keySet().iterator().next();
        assertTrue(mealsByCategories.containsKey(unknownKey));
    }


    @Test
    public void whenCallingMealsByCategoriesWithTypo_ShouldNotReturnCategories(){
        Map<String, List<Meal>> mealsByCategories = mealsTestData.mealsByCategories();
        assertFalse(mealsByCategories.containsKey("Fash"));

    }

    @Test
    public void whenCallingRequestLog_ShouldMatchTheCategories(){
        HashMap<String, Integer> request = new HashMap<>();
        request.put("Chicken", 2);
        request.put("Fish", 1);
        request.put("Meat", 2);
        request.put("Vegetarian", 2);

        Map<String, List<Meal>> mealsByCategories = mealsTestData.mealsByCategories();

        assertEquals(mealsByCategories.keySet(), request.keySet());

    }


    @Test
    public void whenArrangingMealsByCategories_ShouldNotBeNull(){
        Map<String, List<Meal>> mealsByCategories = mealsTestData.mealsByCategories();
        assertNotNull(mealsByCategories);
    }

    @Test
    public void whenGeneratingWeeklyMealsWithEmptyRequest_ShouldReturnSevenMeals(){
        HashMap<String, Integer> request = new HashMap<>();
        HashMap<DaysOfTheWeek, Meal> weeklyPlan = mealsTestData.generateWeeklyMeals(request);

        assertEquals(weeklyPlan.size(), 7);
    }



    @Test
    public void whenGeneratingWeeklyPlan_ShouldReturnAllUniqueMeals(){
        HashMap<String, Integer> request = new HashMap<>();
        HashMap<DaysOfTheWeek, Meal> weeklyPlan = mealsTestData.generateWeeklyMeals(request);

        int uniqueCount = (int) weeklyPlan.values().stream().distinct().count();

        assertEquals(uniqueCount, 7);
    }

    @Test
    public void whenUserInputsRequest_ShouldReturnNotReturnNull(){
        HashMap<String, Integer> userInput = new HashMap<>();
        userInput.put("Chicken", 3);
        userInput.put("Fish", 1);
        userInput.put("Vegetarian", 3);

        HashMap<String, Integer> request = mealsTestData.requestLog(userInput);

        assertNotNull(request);
    }


    @Test
    public void whenUserInputsRequest_ShouldReturnSameRequest(){
        HashMap<String, Integer> userInput = new HashMap<>();
        userInput.put("Chicken", 3);
        userInput.put("Fish", 1);
        userInput.put("Vegetarian", 3);
        HashMap<String, Integer> request = mealsTestData.requestLog(userInput);

        assertEquals(request, userInput);
    }


    @Test
    public void whenUserInputsRequestIsMoreThanRecipes_ShouldThrowError(){
        HashMap<String, Integer> userInput = new HashMap<>();
        Map<String, List<Meal>> mealsByCategories = mealsTestData.mealsByCategories();

        userInput.put("Fish", 3);
        userInput.put("Vegetarian", 5);
        HashMap<String, Integer> request = mealsTestData.requestLog(userInput);

        int mealCategorySize = mealsByCategories.get("Vegetarian").size();
        int userDefined = request.get("Vegetarian");
        assertNotEquals(mealCategorySize, userDefined);

        assertEquals(mealsByCategories.get("Fish").size(), request.get("Fish"));




    }













}
