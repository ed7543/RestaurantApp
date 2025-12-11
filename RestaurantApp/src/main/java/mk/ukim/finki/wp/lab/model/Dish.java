package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.ukim.finki.wp.lab.model.enums.RankDishes;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    //private static long counter = 6L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dishId;
    private String name;
    private String cuisine;
    private int preparationTime;
    @Enumerated(EnumType.STRING)
    private RankDishes rank;
    private double rating;
    @ManyToOne
    @JoinColumn(name = "chef_id")
    private Chef chef;


    public Dish(String dishId, String name, String cuisine, int preparationTime) {
        //this.id = counter++;
       // this.id = id;
        this.dishId = dishId;
        this.name = name;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
    }

    public Dish(Long id, String dishId, String name, String cuisine, int preparationTime) {
      //  this.id = counter++;
        this.dishId = dishId;
        this.name = name;
        this.cuisine = cuisine;
        this.preparationTime = preparationTime;
    }

//    public static long getCounter() {
//        return counter;
//    }
//
//    public static void setCounter(long counter) {
//        Dish.counter = counter;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getPreparation() {
        return preparationTime;
    }

    public void setPreparation(int preparation) {
        this.preparationTime = preparation;
    }
}
