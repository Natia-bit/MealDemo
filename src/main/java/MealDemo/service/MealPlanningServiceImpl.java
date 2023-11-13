package MealDemo.service;

import MealDemo.dao.FrequencyRepository;
import MealDemo.dao.MealRepository;
import MealDemo.entity.Frequency;
import MealDemo.entity.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MealPlanningServiceImpl implements MealPlanningService {

    private MealRepository mealRepository;
    private FrequencyRepository frequencyRepository;


    @Autowired
    public MealPlanningServiceImpl(MealRepository mealRepository, FrequencyRepository frequencyRepository) {
        this.mealRepository = mealRepository;
        this.frequencyRepository = frequencyRepository;
    }

    @Override
    public List<Meal> getMeals() {
        return mealRepository.findAll();
    }

    @Override
    public List<Frequency> getFrequencies() {
        return frequencyRepository.findAll();
    }

    @Override
    public Optional<Meal> findMealById(int mealId) {
        return mealRepository.findById(mealId);
    }


    @Override
    public void saveNewMeal(Meal meal) {
        System.out.println("Saving meal");
        Frequency tempFreq = new Frequency();
        tempFreq.setMeal(meal);

        frequencyRepository.save(tempFreq);

        System.out.println("meal saved! ");
    }

    @Override
    public boolean deleteMeal(int mealId) {

       Optional<Meal> tempMeal = mealRepository.findById(mealId);
        if (tempMeal.isEmpty()){
            System.out.println("Could not find meal");
        } else {
            mealRepository.deleteById(mealId);
            System.out.println("Meal Deleted");
        }

        return tempMeal.isPresent();
    }

    @Override
    public void updateMeal(int mealId, Meal meal) {
        // if cant find mealId will show 404 error cant find ID
        Meal existingMeal = mealRepository.getById(mealId);


        System.out.println("Recipe to edit " + existingMeal.getClass().getName() + " with id " + mealId);

        existingMeal.setMealName(meal.getMealName());
        existingMeal.setCategory(meal.getCategory());

        mealRepository.save(existingMeal);

        System.out.println("meal has been updated. Meal details \n" + existingMeal.toString());
    }


    @Override
    public Map<String, List<Meal>> mealsByCategories() {

        List<Meal> allMeals = getMeals();
        Map<String, List<Meal>> mealsByCategoriesList = new HashMap<>();

        for (Meal meal : allMeals){
            mealsByCategoriesList.computeIfAbsent(meal.getCategory(), k -> new ArrayList<>()).add(meal);
        }

        return  mealsByCategoriesList;
    }

    private int randomNumberGenerator(int min, int max){
        Random random = new Random();
        return random.nextInt(min, max);
    }

    private int randomNumberGenerator(int max){
        Random random = new Random();
        return random.nextInt(max);
    }



    @Override
    // generate 7 random meals
    public HashMap<DaysOfTheWeek, Meal> generateWeeklyMeals() {

        HashMap<DaysOfTheWeek, Meal> weeklyMeals = new HashMap<>();


        HashMap<String, Integer> weeklyMealOccurrence = new HashMap<>();

        /*for (Meal meal : allMeals){
            if (!weeklyMealOccurrence.containsKey(meal.getCategory())){
                weeklyMealOccurrence.put(meal.getCategory(), 1);
            }
        }*/

        weeklyMealOccurrence.put("Chicken", 1);
        weeklyMealOccurrence.put("Fish", 1);
        weeklyMealOccurrence.put("Meat", 2);
        weeklyMealOccurrence.put("Vegetarian", 3);
        System.out.println( "Weekly meal occurrences " + weeklyMealOccurrence);


        for (DaysOfTheWeek day : DaysOfTheWeek.values()){

            System.out.println(day);;

        }




        for (Map.Entry<String, Integer> entry : weeklyMealOccurrence.entrySet()){
            String mealCat = entry.getKey(); //category: Chicken, fish, veg, etc
            int weeklyOccurrence = entry.getValue(); // how many times in a week i ll have that
//            System.out.println( "For loop: " + mealCat + " " + weeklyOccurrence);
            Collections.shuffle(allMeals);
            for (Meal meal : allMeals){
                    if ((meal.getCategory().equals(mealCat)) && weeklyOccurrence > 0){
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.MONDAY, allMeals.get(0));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.TUESDAY, allMeals.get(1));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.WEDNESDAY, allMeals.get(2));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.THURSDAY, allMeals.get(3));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.FRIDAY, allMeals.get(4));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.SATURDAY, allMeals.get(5));
                            weeklyMeals.putIfAbsent(DaysOfTheWeek.SUNDAY, allMeals.get(6));


//                        System.out.println( "Category " + meal.getCategory() + " Meal name " + meal.getMealName());
                        weeklyOccurrence --;
                        weeklyMealOccurrence.put(mealCat, weeklyOccurrence);
                    }
                }
        }


        System.out.println(weeklyMeals);


        return weeklyMeals;

    }

    @Override
    public String test(){
        String testing = " Testing . . . ";
        return testing;
    }




}
