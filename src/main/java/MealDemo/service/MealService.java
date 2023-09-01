package MealDemo.service;

import MealDemo.entity.Meals;

import java.util.List;

public interface MealService {
    // CRUD ops
    List<Meals> findAll();
    Meals findById(int theID);
    Meals save(Meals meal);
    void deleteByID(int theID);
}
