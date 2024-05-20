package MealDemo.service;

import MealDemo.dao.FrequencyRepository;
import MealDemo.dao.MealRepository;
import MealDemo.entity.Meal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MealPlanningTest {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FrequencyRepository frequencyRepository;


    private MealPlanningServiceImpl mealsTest;

    @Before
    public void setUp() {
        System.out.println("Setting up");

        mealsTest = new MealPlanningServiceImpl(mealRepository, frequencyRepository);
        mealsTest.saveNewMeal(new Meal("Chicken pie", "Chicken"));
        mealsTest.saveNewMeal(new Meal("Beef Burger", "Meat"));
        mealsTest.saveNewMeal(new Meal("Fish bake", "Fish"));
        mealsTest.saveNewMeal(new Meal("Bean Enchiladas", "Vegetarian"));

        mealsTest.saveNewMeal(new Meal("Chicken ozro", "Chicken"));
        mealsTest.saveNewMeal(new Meal("Chili con carne", "Meat"));
        mealsTest.saveNewMeal(new Meal("Tuna salad", "Fish"));
        mealsTest.saveNewMeal(new Meal("Falafel with rice", "Vegetarian"));

        mealsTest.saveNewMeal(new Meal("Creamy Tuscan chicken", "Chicken"));
        mealsTest.saveNewMeal(new Meal("Steak and buckwheat", "Meat"));
        mealsTest.saveNewMeal(new Meal("Smoky chorizo salmon", "Fish"));
        mealsTest.saveNewMeal(new Meal("Gnocchi & tomato bake", "Vegetarian"));

        mealsTest.saveNewMeal(new Meal("Chicken fried rice ", "Chicken"));
        mealsTest.saveNewMeal(new Meal("Beef stew", "Meat"));
        mealsTest.saveNewMeal(new Meal("Sushi", "Fish"));
        mealsTest.saveNewMeal(new Meal("Mushroom quesadillas", "Vegetarian"));

    }

    @After
    public void tearDown() {
        System.out.println("Tearing down");

    }

    @Test
    public void whenSaveNewMealChicken_ThenReturnMealChicken(){
        assertEquals(mealsTest.getMeals().get(0).getMealName(), "Chicken pie");
        assertEquals(mealsTest.getMeals().get(0).getCategory(), "Chicken");
        assertEquals(mealsTest.getMeals().get(0).getId(), 1);
    }

    @Test
    public void whenSaveNewMealBeef_ThenReturnMealBeef(){
        assertEquals(mealsTest.getMeals().get(1).getMealName(), "Beef Burger");
        assertEquals(mealsTest.getMeals().get(1).getCategory(), "Meat");
        assertNotEquals(mealsTest.getMeals().get(1).getCategory(), "Chicken");
    }

    @Test
    public void whenGivenMealId_ShouldReturnMealWithSameId(){
        // with if statement check that data is present as the method returns Optional<>
        if (mealsTest.findMealById(1).isPresent()){
            assertEquals(mealsTest.getMeals().get(0).getMealName(),
                         mealsTest.findMealById(1).get().getMealName());
        }


        if (mealsTest.findMealById(2).isPresent()){
            assertEquals(mealsTest.getMeals().get(1).getMealName(),
                         mealsTest.findMealById(2).get().getMealName());
        }

        if (mealsTest.findMealById(1).isPresent() && mealsTest.findMealById(2).isPresent()){
            assertNotEquals(mealsTest.findMealById(1).get().getMealName(),
                            mealsTest.getMeals().get(2).getMealName());
        }
    }

    @Test
    public void whenUpdatingMealName_ShouldReturnNewName(){
        Meal newMealDetails = new Meal("New name", "New category");
        var existingMeal = mealsTest.findMealById(11);

        if (existingMeal.isPresent()){
            mealsTest.updateMeal(11, newMealDetails);
            assertEquals(mealsTest.getMeals().get(10).getMealName(), "New name");
            assertNotEquals(mealsTest.getMeals().get(10).getMealName(), "Smoky chorizo salmon");
            assertEquals(mealsTest.getMeals().size(), 16);
        }
    }


    @Test
    public void whenDeletingMealWithId_ShouldRemoveMeal(){

        mealsTest.deleteMeal(7);
        assertThrowsExactly(ResponseStatusException.class, () -> mealsTest.findMealById(7));
        assertEquals(mealsTest.getMeals().size(), 15);

        mealsTest.deleteMeal(12);
        assertThrowsExactly(ResponseStatusException.class, () -> mealsTest.findMealById(12));
        assertEquals(mealsTest.getMeals().size(), 14);


        assertTrue(mealsTest.deleteMeal(16));
        assertThrowsExactly(ResponseStatusException.class, () -> mealsTest.findMealById(16));
        assertEquals(mealsTest.getMeals().size(), 13);

    }


    @Test
    public void whenCallingMealsByCategoryChicken_ShouldReturnSameCategoryChicken(){
        Map<String, List<Meal>> mealsByCategories = mealsTest.mealsByCategories();
        assertTrue(mealsByCategories.containsKey("Chicken"));
    }

    @Test
    public void whenCallingMealsByCategoryMeat_ShouldReturnSameCategoryMeat(){
        Map<String, List<Meal>> mealsByCategories = mealsTest.mealsByCategories();
        assertTrue(mealsByCategories.containsKey("Meat"));
    }

    @Test
    public void whenCallingMealsByCategoryFish_ShouldReturnSameCategoryFish(){
        Map<String, List<Meal>> mealsByCategories = mealsTest.mealsByCategories();
        assertTrue(mealsByCategories.containsKey("Fish"));
    }

    @Test
    public void whenCallingMealsByCategoryVegetarian_ShouldReturnSameCategoryVegetarian(){
        Map<String, List<Meal>> mealsByCategories = mealsTest.mealsByCategories();
        assertTrue(mealsByCategories.containsKey("Vegetarian"));
    }


    @Test
    public void whenCallingMealsByCategoriesUnknown_ShouldReturnSameCategories(){
        Map<String, List<Meal>> mealsByCategories = mealsTest.mealsByCategories();
        String unknownKey = mealsByCategories.keySet().iterator().next();
        assertTrue(mealsByCategories.containsKey(unknownKey));
    }


    @Test
    public void whenCallingMealsByCategoriesWithTypo_ShouldNotReturnCategories(){
        Map<String, List<Meal>> mealsByCategories = mealsTest.mealsByCategories();
        assertFalse(mealsByCategories.containsKey("Fash"));

    }

    @Test
    public void whenCallingRequestLog_ShouldMatchTheCategories(){
        HashMap<String, Integer> request = new HashMap<>();
        request.put("Chicken", 3);
        request.put("Fish", 2);
        request.put("Meat", 0);
        request.put("Vegetarian", 2);

        Map<String, List<Meal>> mealsByCategories = mealsTest.mealsByCategories();

        assertEquals(mealsByCategories.keySet(), request.keySet());
    }


    @Test
    public void whenArrangingMealsByCategories_ShouldNotBeNull(){
        Map<String, List<Meal>> mealsByCategories = mealsTest.mealsByCategories();
        assertNotNull(mealsByCategories);
    }

    @Test
    public void whenGeneratingWeeklyMealsWithEmptyRequest_ShouldReturnSevenMeals(){
        HashMap<String, Integer> request = new HashMap<>();
        Map<DayOfWeek, Meal> weeklyPlan = mealsTest.generateWeeklyMeals(request);

        assertEquals(weeklyPlan.size(), 7);
    }

    @Test
    public void whenGeneratingWeeklyPlan_ShouldReturnAllUniqueMeals(){
        HashMap<String, Integer> request = new HashMap<>();
        Map<DayOfWeek, Meal> weeklyPlan = mealsTest.generateWeeklyMeals(request);

        int uniqueCount = (int) weeklyPlan.values().stream().distinct().count();

        assertEquals(uniqueCount, 7);
    }

    @Test
    public void whenGenerateWeeklyPlanWithZeroVeg_ShouldReturnWithoutThatCategory(){
        HashMap<String, Integer> request = new HashMap<>();
        request.put("Chicken", 3);
        request.put("Fish", 2);
        request.put("Meat", 2);
        request.put("Vegetarian", 0);

        Map<DayOfWeek, Meal> weeklyPlan = mealsTest.generateWeeklyMeals(request);

        for (var meal : weeklyPlan.values()){
            assertNotEquals(meal.getCategory(), "Vegetarian");
        }
    }

    @Test
    public void whenGenerateWeeklyPlanWithZeroChicken_ShouldReturnWithoutThatCategory(){
        HashMap<String, Integer> request = new HashMap<>();
        request.put("Chicken", 0);
        request.put("Fish", 2);
        request.put("Meat", 2);
        request.put("Vegetarian", 3);

        Map<DayOfWeek, Meal> weeklyPlan = mealsTest.generateWeeklyMeals(request);

        for (var meal : weeklyPlan.values()){
            assertNotEquals(meal.getCategory(), "Chicken");
        }
    }

    @Test
    public void whenGenerateWeeklyPlanWithZeroFish_ShouldReturnWithoutThatCategory(){
        HashMap<String, Integer> request = new HashMap<>();
        request.put("Chicken", 2);
        request.put("Fish", 0);
        request.put("Meat", 2);
        request.put("Vegetarian", 3);

        Map<DayOfWeek, Meal> weeklyPlan = mealsTest.generateWeeklyMeals(request);

        for (var meal : weeklyPlan.values()){
            assertNotEquals(meal.getCategory(), "Fish");
        }
    }

    @Test
    public void whenGenerateWeeklyPlanWithZeroMeat_ShouldReturnWithoutThatCategory(){
        HashMap<String, Integer> request = new HashMap<>();
        request.put("Chicken", 2);
        request.put("Fish", 2);
        request.put("Meat", 0);
        request.put("Vegetarian", 3);

        Map<DayOfWeek, Meal> weeklyPlan = mealsTest.generateWeeklyMeals(request);

        for (var meal : weeklyPlan.values()){
            assertNotEquals(meal.getCategory(), "Meat");
        }
    }


    @Test
    public void whenGenerateWeeklyPlanWithZeroFishAndVeg_ShouldReturnWithoutThatCategories(){
        HashMap<String, Integer> request = new HashMap<>();
        request.put("Chicken", 3);
        request.put("Fish", 0);
        request.put("Meat", 4);
        request.put("Vegetarian", 0);

        Map<DayOfWeek, Meal> weeklyPlan = mealsTest.generateWeeklyMeals(request);

        for (var meal : weeklyPlan.values()){
            assertNotEquals(meal.getCategory(), "Fish");
            assertNotEquals(meal.getCategory(), "Vegetarian");
        }
    }
}
