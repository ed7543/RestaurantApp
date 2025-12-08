package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Chef;

import java.util.List;

public interface ChefService {
    List<Chef> listChefs();
    Chef findById(Long id);
    Chef addDishToChef(Long chefId, Long dishId);
    Chef create(String firstName, String lastName, String bio, String gender);
    Chef update(Long id, String firstName, String lastName, String bio, String gender);
    void delete(Long id);
}
