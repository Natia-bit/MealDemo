package MealDemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="meals")
public class Meals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="meal_name")
    private String mealName;

    @Column(name="category")
    private String category;

    @OneToOne(mappedBy = "frequency")
    private Frequency frequency;

    //CONSTRUCTORS
    public Meals() {
    }

    public Meals(String mealName, String category) {
        this.mealName = mealName;
        this.category = category;
    }

    //GETTER AND SETTER
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

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    //TO STRING

    @Override
    public String toString() {
        return "Meals{" +
                "id=" + id +
                ", mealName='" + mealName + '\'' +
                ", category='" + category + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
