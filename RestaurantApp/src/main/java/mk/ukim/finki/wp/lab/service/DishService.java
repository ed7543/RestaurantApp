package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.model.enums.RankDishes;

import java.util.List;

public interface DishService {
    List<Dish> listDishes();
    Dish findByDishId(String dishId);
    Dish findById(Long id);
    Dish create(String dishId, String name, String cuisine, int preparationTime);
    Dish update(Long id, String dishId, String name, String cuisine, int preparationTime);
    void delete(Long id);
    List<Dish> search(String name, String cuisine, Integer preparationTime, RankDishes rank, Double rating);
}
