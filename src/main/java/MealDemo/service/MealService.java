package MealDemo.service;

import MealDemo.entity.Meal;

import java.util.List;

public interface MealService {
    // CRUD ops
    List<Meal> findAll();
    Meal findById(int theID);
    Meal save(Meal meal);
    void deleteByID(int theID);
}
