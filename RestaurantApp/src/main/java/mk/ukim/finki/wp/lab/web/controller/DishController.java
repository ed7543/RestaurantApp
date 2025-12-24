package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.model.enums.RankDishes;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dishes")
public class DishController {
    private final DishService dishService;
    private final ChefService chefService;

    public DishController(DishService dishService, ChefService chefService) {
        this.dishService = dishService;
        this.chefService = chefService;
    }
//    @GetMapping
//    public String getDishesPage(@RequestParam(required = false) String error, Model model) {
//        if (error != null) {
//            model.addAttribute("error", error);
//        }
//
//        List<Dish> dishes = dishService.listDishes();
//        model.addAttribute("dishes", dishes);
//
//        return "listDishes";
//    }

    @GetMapping
    public String getDishesPage(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) Integer preparationTime,
            @RequestParam(required = false) RankDishes rank,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) String error,
            Model model) {

        if (error != null) {
            model.addAttribute("error", error);
        }

        List<Dish> dishes = dishService.search(name, cuisine, preparationTime, rank, rating);
        model.addAttribute("dishes", dishes);

        return "listDishes";
    }


    @GetMapping("/dish-form/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditDishForm(@PathVariable Long id, Model model) {
        Dish dish = dishService.findById(id);
        if (dish == null) {
            return "redirect:/dishes?error=DishNotFound";
        }

        model.addAttribute("dish", dish);
        model.addAttribute("chefs", chefService.listChefs());

        return "dish-form";
    }

    @GetMapping("/dish-form")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAddDishForm(Model model) {
        model.addAttribute("dish", new Dish());
        model.addAttribute("chefs", chefService.listChefs());

        return "dish-form";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String saveDish(@RequestParam String dishId, @RequestParam String name, @RequestParam String cuisine, @RequestParam int preparationTime, @RequestParam RankDishes rankDishes, @RequestParam Double rating) {
        try {
            dishService.create(dishId, name, cuisine, preparationTime, rankDishes, rating);
        } catch (IllegalArgumentException e) {
            return "redirect:/dishes?error=" + e.getMessage();
        }

        return "redirect:/dishes";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editDish(@PathVariable Long id, @RequestParam String dishId, @RequestParam String name, @RequestParam String cuisine, @RequestParam int preparationTime,  @RequestParam RankDishes rankDishes,  @RequestParam Double rating) {
        try {
            dishService.update(id, dishId, name, cuisine, preparationTime, rankDishes, rating);
        } catch (RuntimeException e) {
            return "redirect:/dishes?error=" + e.getMessage();
        }

        return "redirect:/dishes";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteDish(@PathVariable Long id) {
        try {
            dishService.delete(id);
        } catch (RuntimeException e) {
            return "redirect:/dishes?error=" + e.getMessage();
        }

        return "redirect:/dishes";
    }
}
