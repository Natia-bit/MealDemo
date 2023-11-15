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
        if (tempMeal.isEmpty()) {
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


    @Override
    public Map<String, List<Meal>> mealsByCategories() {
        List<Meal> allMeals = getMeals();
        Map<String, List<Meal>> mealsByCategoriesList = new HashMap<>();
        for (Meal meal : allMeals) {
            mealsByCategoriesList.computeIfAbsent(meal.getCategory(), k -> new ArrayList<>()).add(meal);
        }

        return mealsByCategoriesList;
    }

    private int randomNumberGenerator(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }


    @Override
    // generate 7 random meals per each day
    public HashMap<DaysOfTheWeek, Meal> generateWeeklyMeals() {

        HashMap<DaysOfTheWeek, Meal> weeklyPlan = new HashMap<>();
        Map<String, List<Meal>> mealsByCategories = mealsByCategories();

        // create a method ↓
        HashMap<String, Integer> request = new HashMap<>();
        request.put("Chicken", 2);
        request.put("Fish", 1);
        request.put("Meat", 2);
        request.put("Vegetarian", 2);


        for (DaysOfTheWeek day : DaysOfTheWeek.values()) {
            String randomCategory = (String) request.keySet().toArray()[new Random().nextInt(request.keySet().toArray().length)];
            int requestNumber = request.get(randomCategory);

            while (requestNumber == 0) {
                randomCategory = (String) request.keySet().toArray()[new Random().nextInt(request.keySet().toArray().length)];
                requestNumber = request.get(randomCategory);
            }

            if ((requestNumber > 0)) {
                for (Map.Entry<String, List<Meal>> entry : mealsByCategories.entrySet()) {
                    if (entry.getKey().contains(randomCategory)) {
                        Meal randomMeal = entry.getValue().get(randomNumberGenerator(entry.getValue().size()));
                        weeklyPlan.putIfAbsent(day, randomMeal);
                        requestNumber--;
                        if (!mealsByCategories.get(randomCategory).isEmpty()) {
                            mealsByCategories.get(randomCategory).remove(randomMeal);
                        }
                        break;
                    }
                }
            }
            request.replace(randomCategory, requestNumber);
        }


        return weeklyPlan;
    }

    @Override
    public String test() {
        String testing = " Testing . . . ";
        return testing;
    }


}
