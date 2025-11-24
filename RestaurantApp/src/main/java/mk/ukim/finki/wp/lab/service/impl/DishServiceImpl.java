package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final ChefService chefService;

    public DishServiceImpl(DishRepository dishRepository, ChefService chefService) {
        this.dishRepository = dishRepository;
        this.chefService = chefService;
    }


    @Override
    public List<Dish> listDishes() {
        return this.dishRepository.findAll();
    }

    @Override
    public Dish findByDishId(String dishId) {
        return this.dishRepository.findByDishId(dishId);
    }

    @Override
    public Dish findById(Long id) {
        return this.dishRepository.findById(id).orElse(null);
    }

    @Override
    public Dish create(String dishId, String name, String cuisine, int preparationTime) {
        if(dishId == null || name == null || cuisine == null || preparationTime <= 0) {
            throw new RuntimeException("Invalid parameters");
        }

        if (dishRepository.findByDishId(dishId) != null) {
            throw new IllegalArgumentException("Dish with this ID already exists!");
        }

        Dish dish = new Dish(dishId, name, cuisine, preparationTime);

        return this.dishRepository.save(dish);
    }

    @Override
    public Dish update(Long id, String dishId, String name, String cuisine, int preparationTime) {
        if(dishId == null || name == null || cuisine == null || preparationTime <= 0) {
            throw new RuntimeException("Invalid parameters");
        }


        Dish dish = this.dishRepository.findById(id).orElseThrow(() -> new RuntimeException("Dish with id: " + id + " not found"));
        dish.setDishId(dishId);
        dish.setName(name);
        dish.setCuisine(cuisine);
        dish.setPreparation(preparationTime);

        return this.dishRepository.save(dish);
    }

    @Override
    public void delete(Long id) {
        this.dishRepository.deleteById(id);
    }
}
