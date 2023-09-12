package MealDemo.service;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meals;
import MealDemo.repository.FrequencyRepository;
import MealDemo.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealPlanningService implements IMealPlanningService {

    // =========== REPOSITORIES ===========
    private MealRepository mealRepository;
    private FrequencyRepository frequencyRepository;


    // =========== CONSTRUCTOR ===========
    @Autowired
    public MealPlanningService(MealRepository mealRepository, FrequencyRepository frequencyRepository) {
        this.mealRepository = mealRepository;
        this.frequencyRepository = frequencyRepository;
    }

    @Autowired
    public MealPlanningService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @Autowired
    public MealPlanningService(FrequencyRepository frequencyRepository) {
        this.frequencyRepository = frequencyRepository;
    }

    // =========== IMPLEMENTATIONS ===========
    // VIEW ALL
    @Override
    public void getAllMeals(){
//        mealRepository.findAll();
        frequencyRepository.findAll();
    }

    // GET MEALS & SEQUENCES
    @Override
    public List<Meals> getMeals() {
        return mealRepository.findAll();
    }

    @Override
    public List<Frequency> getFrequencies() {
        return frequencyRepository.findAll();
    }

    // FIND BY ID
    @Override
    public Meals findMealById(int id) {
        Optional<Meals> result = mealRepository.findById(id);

        Meals meal = null;
        if (result.isPresent()) {
            meal = result.get();
        } else {
            throw new RuntimeException("Meal id not found");
        }

        return meal;
    }

    // find by id
    @Override
    public Frequency findFreqByTheId(int id) {

        Optional <Frequency> result = frequencyRepository.findById(id);

        Frequency frequency = null;

        if (result.isPresent()){
            frequency = result.get();
        } else {
            throw new RuntimeException("didnt find meal no " + id);
        }

        return frequency;
    }

    // SAVE NEW OR UPDATE
    @Override
    public Meals saveNewMeal(Meals meal) {
        System.out.println("Saving meal");
        Frequency tempFreq = new Frequency();
        tempFreq.setMeal(meal);

        frequencyRepository.save(tempFreq);

        return mealRepository.save(meal);
    }

    // DELETE
    @Override
    public void deleteMeal(int id) {
        frequencyRepository.deleteById(id);
        mealRepository.deleteById(id);
    }


    // update


    @Override
    public void updateMeal(int mealId, Meals meal) {
        Meals existingMeal = mealRepository.getById(mealId);

        System.out.println("Recipe to edit " + existingMeal.getMealName() + " with id " + mealId);



    }
}