package MealDemo.service;

import MealDemo.dao.MealRepository;
import MealDemo.entity.Meal;
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
    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    // F: Change the return type of this method to Optional<Meal>
    // F: That way the caller of this method can decide what to do should the meal not be present.
    // F: Bad design to pass nulls around when you have nice Optionals to work with.
    @Override
    public Meal findById(int theID) {
       Optional<Meal> result = mealRepository.findById(theID);

       Meal meal = null;

       if (result.isPresent()){
           meal = result.get();
       } else {
           throw new RuntimeException("didnt find meal no " + theID);
       }

       return meal;
    }

    @Override
    public Meal save(Meal meal) {
        return mealRepository.save(meal);
    }

    //F: Make return type boolean -DONE
    @Override
    public void deleteByID(int theID) {
        //F: put find logic here
        mealRepository.deleteById(theID);
    }
}
