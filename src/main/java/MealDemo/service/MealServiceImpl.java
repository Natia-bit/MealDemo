package MealDemo.service;

import MealDemo.repository.MealRepository;
import MealDemo.entity.Meals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealServiceImpl implements MealService{

    private MealRepository mealRepository;

    @Autowired
    public MealServiceImpl(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    // CRUD METHODS

    @Override
    public List<Meals> findAll() {
        return mealRepository.findAll();
    }

    @Override
    public Meals findById(int theID) {
       Optional<Meals> result = mealRepository.findById(theID);

       Meals meal = null;

       if (result.isPresent()){
           meal = result.get();
       } else {
           throw new RuntimeException("didnt find meal no " + theID);
       }

       return meal;
    }

    @Override
    public Meals save(Meals meal) {
        return mealRepository.save(meal);
    }

    @Override
    public void deleteByID(int theID) {
        mealRepository.deleteById(theID);
    }
}
