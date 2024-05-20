package MealDemo.service;

import MealDemo.dao.FrequencyRepository;
import MealDemo.dao.MealRepository;
import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.DayOfWeek;

import java.util.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@Service
public class MealPlanningServiceImpl implements MealPlanningService {

    private final MealRepository mealRepository;
    private final FrequencyRepository frequencyRepository;




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
        var tempMeal = mealRepository.findById(mealId);

        Meal meal = null;
        if (tempMeal.isPresent()){
            meal = tempMeal.get();
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Invalid meal ID");
        }

        return Optional.of(meal);
    }


    @Override
    public void saveNewMeal(Meal meal) {
        var tempFreq = new Frequency();
        tempFreq.setMeal(meal);
        frequencyRepository.save(tempFreq);
        System.out.println("meal saved! ");
    }


    @Transactional
    @Override
    public boolean deleteMeal(int mealId) {
        var isDeleted = false;

        var tempMeal = mealRepository.findById(mealId);
        if (tempMeal.isEmpty()) {
            System.out.println("Could not find meal");
        } else {
            System.out.println("Deleting meal " + tempMeal.get());
            frequencyRepository.deleteById(mealId);
            mealRepository.deleteById(mealId);
            System.out.println("Meal Deleted");
            isDeleted = true;
        }

        return isDeleted;
    }

    @Override
    public void updateMeal(int mealId, Meal meal) {
        // if cant find mealId will show 404 error cant find ID
        var tempMeal = mealRepository.findById(mealId);
        if (tempMeal.isPresent()) {
            var existingMeal = tempMeal.get();
            existingMeal.setMealName(meal.getMealName());
            existingMeal.setCategory(meal.getCategory());
            mealRepository.save(existingMeal);
            System.out.println("meal has been updated. Meal details \n" + existingMeal.toString());
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Invalid meal ID");
        }
    }


    @Override
    public Map<String, List<Meal>> mealsByCategories() {
        var allMeals = getMeals();
        Map<String, List<Meal>> mealsByCategoriesList = new HashMap<>();
        for (var meal : allMeals) {
            mealsByCategoriesList.computeIfAbsent(meal.getCategory(), k -> new ArrayList<>()).add(meal);
        }

        return mealsByCategoriesList;
    }


    public Map<DayOfWeek, Meal> generateWeeklyMeals(HashMap<String, Integer> userInput) {

        if (!userInput.isEmpty()){
            System.out.println("Using custom values");
            int sumRequest = 0;
            for (int requestNumber : userInput.values()){
                sumRequest += requestNumber;
            }

            if (sumRequest != 7){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request: incorrect number of meals requested. You should request 7 meals. One for each day of the week.");
            }
        } else {
            System.out.println("Using default values");
            userInput.put("Chicken", 2);
            userInput.put("Fish", 2);
            userInput.put("Meat", 2);
            userInput.put("Vegetarian", 1);
        }

        HashMap<DayOfWeek, Meal> weeklyPlan = new HashMap<>();
        Map<String, List<Meal>> mealsByCategories = mealsByCategories();
        HashMap<String, Integer> request = new HashMap<>(userInput);
        Random random = new Random();

        request.values().removeIf(occurrence -> occurrence==0);

        String[] categories = request.keySet().toArray(new String[0]);

        for (DayOfWeek day : DayOfWeek.values()) {
            var randomCategory = categories[new Random().nextInt(categories.length)];
            var requestNumber = request.get(randomCategory);

            var meals = mealsByCategories.get(randomCategory);
            var randomMeal = meals.get(random.nextInt(meals.size()));
            weeklyPlan.putIfAbsent(day, randomMeal);
            requestNumber --;

            if (!meals.isEmpty()) {
                meals.remove(randomMeal);
            }

            if (requestNumber == 0) {
                request.remove(randomCategory);
                categories = request.keySet().toArray(new String[0]);
            } else {
                request.replace(randomCategory, requestNumber);
            }
        }

        return weeklyPlan;
    }

    public Meal randomMeal(){
        Random random = new Random();
        var allMeals = getMeals();
        int randomIndex = random.nextInt(allMeals.size());
        return allMeals.get(randomIndex);
    }

}