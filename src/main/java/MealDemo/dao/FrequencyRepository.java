package MealDemo.dao;

import MealDemo.entity.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrequencyRepository extends JpaRepository<Frequency, Integer> {
    // entity: Frequency
    // primary key: type INTEGER (id)


}
