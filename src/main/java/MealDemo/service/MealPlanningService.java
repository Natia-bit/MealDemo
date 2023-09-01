package MealDemo.service;

import MealDemo.dao.FrequencyRepository;
import MealDemo.dao.MealRepository;
import MealDemo.entity.Frequency;
import MealDemo.entity.Meals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealPlanningService implements IMealPlanningService {

    private MealRepository mealRepository;
    private FrequencyRepository frequencyRepository;

    @Autowired
    public MealPlanningService(MealRepository mealRepository, FrequencyRepository frequencyRepository) {
        this.mealRepository = mealRepository;
        this.frequencyRepository = frequencyRepository;
    }

    // METHODS
    // see all (including frequency)
    @Override
    public void seeAllMeals(){
//        mealRepository.findAll();
        frequencyRepository.findAll();
    }


    @Override
    public List<Meals> viewAllMeals() {
        return mealRepository.findAll();
    }

    @Override
    public List<Frequency> viewAllFreq() {
        return frequencyRepository.findAll();
    }

    // find by id

    @Override
    public Frequency findByTheId(int id) {

        Optional <Frequency> result = frequencyRepository.findById(id);

        Frequency frequency = null;

        if (result.isPresent()){
            frequency = result.get();
        } else {
            throw new RuntimeException("didnt find meal no " + id);
        }

        return frequency;
    }

    // Add new meal and update freq

    @Override
    public Meals addNewMeal(Meals meal) {
        System.out.println("Saving meal");
        Frequency tempFreq = new Frequency();
        tempFreq.setMeal(meal);

        frequencyRepository.save(tempFreq);

        return mealRepository.save(meal);
    }


    // update / edit

    // remove


}
