package MealDemo.service;

import MealDemo.dao.FrequencyRepository;
import MealDemo.dao.MealRepository;
import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MealPlanningServiceImpl implements MealPlanningService {

    private MealRepository mealRepository;
    private FrequencyRepository frequencyRepository;


    @Autowired
    public MealPlanningServiceImpl(MealRepository mealRepository, FrequencyRepository frequencyRepository) {
        this.mealRepository = mealRepository;
        this.frequencyRepository = frequencyRepository;
    }

    @Override
    public List<Meal> getMeals() {
        return mealRepository.findAll();
    }

    @Override
    public List<Frequency> getFrequencies() {
        return frequencyRepository.findAll();
    }

    @Override
    public Optional<Meal> findMealById(int mealId) {

        return mealRepository.findById(mealId);
    }


    @Override
    public void saveNewMeal(Meal meal) {
        System.out.println("Saving meal");
        Frequency tempFreq = new Frequency();
        tempFreq.setMeal(meal);

        frequencyRepository.save(tempFreq);

        System.out.println("meal saved! ");
    }

    @Override
    public boolean deleteMeal(int mealId) {

       Optional<Meal> tempMeal = mealRepository.findById(mealId);
        if (tempMeal.isEmpty()){
            System.out.println("Could not find meal");
        } else {
            mealRepository.deleteById(mealId);
            System.out.println("Meal Deleted");
        }

        return tempMeal.isPresent();
    }

    @Override
    public void updateMeal(int mealId, Meal meal) {
        // if cant find mealId will show 404 error cant find ID
        Meal existingMeal = mealRepository.getById(mealId);


        System.out.println("Recipe to edit " + existingMeal.getClass().getName() + " with id " + mealId);

        existingMeal.setMealName(meal.getMealName());
        existingMeal.setCategory(meal.getCategory());

        mealRepository.save(existingMeal);

        System.out.println("meal has been updated. Meal details \n" + existingMeal.toString());
    }


    private int randomNumberGenerator(int min, int max){
        Random random = new Random();
        return random.nextInt(min, max);
    }

    private int randomNumberGenerator(int max){
        Random random = new Random();
        return random.nextInt(max);
    }



    @Override
    // generate 7 random meals
    public HashMap<DaysOfTheWeek, Meal> generateWeeklyMeals() {

        HashMap<DaysOfTheWeek, Meal> weeklyMeals = new HashMap<>();

        List<Meal> mealsList = getMeals();
        HashMap<String, Integer> weeklyMealOccurrence = new HashMap<>();

        for (Meal meal : mealsList){
            if (!weeklyMealOccurrence.containsKey(meal.getCategory())){
                weeklyMealOccurrence.put(meal.getCategory(), 1);
            }
        }

        weeklyMealOccurrence.put("Chicken", 2);
        weeklyMealOccurrence.put("Fish", 1);
        weeklyMealOccurrence.put("Meat", 2);
        weeklyMealOccurrence.put("Vegetarian", 2);
        System.out.println( "Weekly meal occurrences " + weeklyMealOccurrence);



        for (Map.Entry<String, Integer> entry : weeklyMealOccurrence.entrySet()){
            String mealCat = entry.getKey();
            int weeklyOccurrence = entry.getValue();

            System.out.println( "For loop: " + mealCat + " " + weeklyOccurrence);
                for (Meal meal : mealsList){
                    if (meal.getCategory().equals(mealCat)){

                        DaysOfTheWeek randomDay = DaysOfTheWeek.values()[randomNumberGenerator(7)];
                        Meal randomMeal = mealsList.get(randomNumberGenerator(mealsList.size()));

                        System.out.println( "Category " + meal.getCategory() + " Meal name " + meal.getMealName());

                        while (weeklyOccurrence > 0){
                            weeklyMeals.put(randomDay, randomMeal);
                            weeklyOccurrence --;
                        }
                    }
                }

        }

        System.out.println(weeklyMeals);

        return weeklyMeals;
    }

    @Override
    public String test(){
        String test = " Testing . . . ";
        return test;
    }








}