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
            System.out.println("Deleting meal " + tempMeal + "with id " + mealRepository.findById(mealId));
            frequencyRepository.deleteById(mealId);
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
    // generate 7 random unique meals per each day
    public Map<DaysOfTheWeek, Meal> generateWeeklyMeals(HashMap<String, Integer> userInput) {
        HashMap<DaysOfTheWeek, Meal> weeklyPlan = new HashMap<>();
        Map<String, List<Meal>> mealsByCategories = mealsByCategories();
        HashMap<String, Integer> request = new HashMap<>(userInput);

        String[] categories = request.keySet().toArray(new String[request.size()]);

        for (DaysOfTheWeek day : DaysOfTheWeek.values()) {
            String randomCategory = categories[new Random().nextInt(categories.length)];
            int requestNumber = request.get(randomCategory);

            List<Meal> meals = mealsByCategories.get(randomCategory);

            Meal randomMeal = meals.get(randomNumberGenerator(meals.size()));
            weeklyPlan.putIfAbsent(day, randomMeal);
            requestNumber --;

            if (!meals.isEmpty()) {
                meals.remove(randomMeal);
            }


            if (requestNumber == 0) {
                request.remove(randomCategory);
                categories = request.keySet().toArray(new String[request.size()]);
            } else {
                request.replace(randomCategory, requestNumber);
            }
        }


        return weeklyPlan;
    }


    public Meal randomMeal(){
        List<Meal> allMeals = getMeals();
        int randomIndex = randomNumberGenerator(allMeals.size());
        return allMeals.get(randomIndex);
    }

}