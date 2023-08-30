package MealDemo.service;

import MealDemo.entity.Meals;

import java.util.List;
import java.util.Optional;

public interface MealService {
    // CRUD ops
    List<Meals> findAll();
    Optional<Meals> findById(int theID);
    Meals save(Meals meal);
    void deleteByID(int theID);
}
