package MealDemo.dao;

import MealDemo.entity.Meals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meals, Integer> {
    // entity: Meals
    // primary key: type INTEGER (id)

}
