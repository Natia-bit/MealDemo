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

        weeklyMealOccurrence.put("Chicken", 1);
        weeklyMealOccurrence.put("Fish", 1);
        weeklyMealOccurrence.put("Meat", 2);
        weeklyMealOccurrence.put("Vegetarian", 3);
        System.out.println( "Weekly meal occurrences " + weeklyMealOccurrence);

        for (Map.Entry<String, Integer> entry : weeklyMealOccurrence.entrySet()){
            String mealCat = entry.getKey(); //category: Chicken, fish, veg, etc
            int weeklyOccurrence = entry.getValue(); // how many times in a week i ll have that
            System.out.println( "For loop: " + mealCat + " " + weeklyOccurrence);

                for (Meal meal : mealsList){
                    while (weeklyOccurrence > 0){
                        Collections.shuffle(mealsList);



                        if (meal.getCategory().equals(mealCat)){

                            weeklyMeals.putIfAbsent(DaysOfTheWeek.MONDAY, mealsList.get(0));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.TUESDAY, mealsList.get(1));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.WEDNESDAY, mealsList.get(2));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.THURSDAY, mealsList.get(3));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.FRIDAY, mealsList.get(4));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.SATURDAY, mealsList.get(5));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.SUNDAY, mealsList.get(6));

                        }
//                        System.out.println( "Category " + meal.getCategory() + " Meal name " + meal.getMealName());
                        weeklyOccurrence --;
                    }
                }
        }


        System.out.println(weeklyMeals);


        return weeklyMeals;

    }

    @Override
    public String test(){
        String testing = " Testing . . . ";
        return testing;
    }




}
