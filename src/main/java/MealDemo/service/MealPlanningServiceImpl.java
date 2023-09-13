package MealDemo.service;

import MealDemo.dao.FrequencyRepository;
import MealDemo.dao.MealRepository;
import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealPlanningServiceImpl implements MealPlanningService {

    // =========== REPOSITORIES ===========
    private MealRepository mealRepository;
    private FrequencyRepository frequencyRepository;


    // =========== CONSTRUCTOR ===========
    @Autowired
    public MealPlanningServiceImpl(MealRepository mealRepository, FrequencyRepository frequencyRepository) {
        this.mealRepository = mealRepository;
        this.frequencyRepository = frequencyRepository;
    }


    // =========== IMPLEMENTATIONS ===========

    // GET MEALS & SEQUENCES
    @Override
    public List<Meal> getMeals() {
        return mealRepository.findAll();
    }

    @Override
    public List<Frequency> getFrequencies() {
        return frequencyRepository.findAll();
    }

    // FIND BY ID
    @Override
    public Optional<Meal> findMealById(int mealId) {
        return mealRepository.findById(mealId);
    }

    // SAVE NEW
    @Override
    public void saveNewMeal(Meal meal) {
        System.out.println("Saving meal");
        Frequency tempFreq = new Frequency();
        tempFreq.setMeal(meal);

        frequencyRepository.save(tempFreq);

        System.out.println("meal saved! ");
    }


    // DELETE
    @Override
    public boolean deleteMeal(int mealId) {
        boolean isDeleted;
        // find
        Optional<Meal> tempMeal = findMealById(mealId);
        if (tempMeal.isEmpty()){
            System.out.println("Could not find meal");
            isDeleted = false;
        } else {
            mealRepository.deleteById(mealId);
            System.out.println("Meal Deleted");
            isDeleted = true;
        }

        return isDeleted;
    }

    // UPDATE
    @Override
    public void updateMeal(int mealId, Meal meal) {
        // find meal
        Meal existingMeal = mealRepository.getById(mealId);

        System.out.println("Recipe to edit " + existingMeal.getMealName() + " with id " + mealId);

        existingMeal.setMealName(meal.getMealName());
        existingMeal.setCategory(meal.getCategory());

        mealRepository.save(existingMeal);

        System.out.println("meal has been updated. Meal details \n" + existingMeal.toString());

    }

}