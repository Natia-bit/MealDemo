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

}