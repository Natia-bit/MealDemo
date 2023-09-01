package MealDemo.dao;

import MealDemo.entity.Frequency;
import MealDemo.entity.Meals;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AppDAOImpl implements  AppDAO{

    private EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Meals meal) {
        Frequency tempFrequency = new Frequency();
        tempFrequency.setMeal(meal);

        entityManager.persist(meal);
        entityManager.persist(tempFrequency);
    }

    @Override
    public Frequency findFrequencyByMeal(int id){
        return entityManager.find(Frequency.class, id);
    }


    @Override
    @Transactional
    public void deleteMealById(int id) {
        Meals tempMeal = entityManager.find(Meals.class, id);
        entityManager.remove(tempMeal);
    }








}
