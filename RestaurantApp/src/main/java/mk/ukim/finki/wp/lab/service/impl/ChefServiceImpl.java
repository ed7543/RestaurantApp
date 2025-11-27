package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import mk.ukim.finki.wp.lab.service.ChefService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChefServiceImpl implements ChefService {
    private final ChefRepository chefRepository;
    private final DishRepository dishRepository;

    public ChefServiceImpl(ChefRepository chefRepository,  DishRepository dishRepository) {
        this.chefRepository = chefRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public List<Chef> listChefs() {
        return this.chefRepository.findAll();
    }

    @Override
    public Chef findById(Long id) {
        return this.chefRepository.findById(id).orElse(null);
    }

    @Override
    public Chef addDishToChef(Long chefId, Long dishId) {
        Chef chef = this.findById(chefId);
        if (chef != null) {
            Dish dish = dishRepository.findById(dishId).orElse(null);
            if (dish != null && !chef.getDishes().contains(dish)) {
                //chef.getDishes().add(dish);
                List<Dish> mutableDishes = new ArrayList<>(chef.getDishes());
                mutableDishes.add(dish);
                chef.setDishes(mutableDishes);
                this.chefRepository.save(chef);
            }
        }
        return chef;
    }

    @Override
    public Chef create(Long id, String firstName, String lastName, String bio, String gender) {
        if(id == null || firstName == null || lastName == null || bio == null || gender == null) {
            throw new RuntimeException("Invalid parameters");
        }

        if(chefRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Chef with this ID already exists!");
        }

        Chef chef = new Chef(id, firstName, lastName, bio, gender);

        return this.chefRepository.save(chef);
    }

    @Override
    public Chef update(Long id, String firstName, String lastName, String bio, String gender) {
        if(id == null || firstName == null || lastName == null || bio == null || gender == null) {
            throw new RuntimeException("Invalid parameters");
        }

        Chef chef = this.chefRepository.findById(id).orElseThrow(() -> new RuntimeException("Chef with id: " + id + " not found."));
        chef.setId(id);
        chef.setFirstName(firstName);
        chef.setLastName(lastName);
        chef.setBio(bio);
        //chef.setDishes(dishes);
        chef.setGender(gender);

        return this.chefRepository.save(chef);
    }

    @Override
    public void delete(Long id) {
       this.chefRepository.deleteById(id);
    }
}
