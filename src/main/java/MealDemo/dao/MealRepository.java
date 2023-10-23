package MealDemo.dao;

import MealDemo.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Integer> {
    // entity: Meals
    // primary key: type INTEGER (id)
}
