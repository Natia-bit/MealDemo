package MealDemo.service;

import MealDemo.entity.Frequency;

import java.util.List;
import java.util.Optional;

public interface FrequencyService {

    List<Frequency> findAll();
    Optional<Frequency> findById(int theID);
    Frequency save(Frequency frequency);
    void deleteByID(int theID);


}
