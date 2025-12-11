package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.model.enums.RankDishes;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import mk.ukim.finki.wp.lab.service.FieldFilterSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static mk.ukim.finki.wp.lab.service.FieldFilterSpecification.*;

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

    @Override
    public List<Dish> search(String name, String cuisine, Integer preparationTime, RankDishes rank, Double rating) {
//        return dishRepository.findAll(
//                (Sort) Specification.allOf(FieldFilterSpecification.filterContainsText(Dish.class, "name", name))
//                        .and(FieldFilterSpecification.filterContainsText(Dish.class, "cuisine", cuisine))
//                        .and(FieldFilterSpecification.greaterThan(Dish.class, "preparationTime", preparationTime))
//                        .and(FieldFilterSpecification.filterEqualsV(Dish.class, "rank", rank))
//                        .and(FieldFilterSpecification.greaterThan(Dish.class, "rating", rating))
//        );
//        //return dishRepository.findAll(specification);

        Specification<Dish> specification = Specification.allOf(
                filterContainsText(Dish.class, "name", name),
                filterContainsText(Dish.class, "cuisine", cuisine),
                greaterThan(Dish.class, "preparationTime", preparationTime),
                filterEqualsV(Dish.class, "rank", rank),
                greaterThan(Dish.class, "rating", rating)
        );

        return this.dishRepository.findAll(specification);
    }
}
