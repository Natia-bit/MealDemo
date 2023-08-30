package MealDemo.entity;

import jakarta.persistence.*;

@Entity
public class Frequency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="times_used")
    private int timesUsed;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meal_id", referencedColumnName = "id")
    private Meals meal;

    //
    //CONSTRUCTORS
    public Frequency() {
    }


    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public Meals getMeal() {
        return meal;
    }

    public void setMeal(Meals meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "Frequency{" +
                "id=" + id +
                ", timesUsed=" + timesUsed +
                ", meal=" + meal +
                '}';
    }
}
