package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dishes")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }
    @GetMapping
    public String getDishesPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }

        List<Dish> dishes = dishService.listDishes();
        model.addAttribute("dishes", dishes);

        return "listDishes";
    }

    @GetMapping("/dish-form/{id}")
    public String getEditDishForm(@PathVariable Long id, Model model) {
        Dish dish = dishService.findById(id);
        if (dish == null) {
            return "redirect:/dishes?error=DishNotFound";
        }

        model.addAttribute("dish", dish);

        return "dish-form";
    }

    @GetMapping("/dish-form")
    public String getAddDishForm(Model model) {
        model.addAttribute("dish", new Dish());

        return "dish-form";
    }

    @PostMapping
    public String saveDish(@RequestParam String dishId, @RequestParam String name, @RequestParam String cuisine, @RequestParam int preparationTime) {
        try {
            dishService.create(dishId, name, cuisine, preparationTime);
        } catch (IllegalArgumentException e) {
            return "redirect:/dishes?error=" + e.getMessage();
        }

        return "redirect:/dishes";
    }

    @PostMapping("/edit/{id}")
    public String editDish(@PathVariable Long id, @RequestParam String dishId, @RequestParam String name, @RequestParam String cuisine, @RequestParam int preparationTime) {
        try {
            dishService.update(id, dishId, name, cuisine, preparationTime);
        } catch (RuntimeException e) {
            return "redirect:/dishes?error=" + e.getMessage();
        }

        return "redirect:/dishes";
    }

    @PostMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        try {
            dishService.delete(id);
        } catch (RuntimeException e) {
            return "redirect:/dishes?error=" + e.getMessage();
        }

        return "redirect:/dishes";
    }
}
