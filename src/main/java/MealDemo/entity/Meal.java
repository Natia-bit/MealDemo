package MealDemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="meal_name")
    private String mealName;

    @Column(name="category")
    private String category;


    public Meal() {
    }

    public Meal(String mealName, String category) {
        this.mealName = mealName;
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    @Override
    public String toString() {
        return "Meals{" +
                "id=" + id +
                ", mealName='" + mealName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
