package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chefs")
public class ChefController {
    private final ChefService chefService;

    public ChefController(ChefService chefService) {
        this.chefService = chefService;
    }

    @GetMapping
    public String getChefsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }

        List<Chef> chefs = chefService.listChefs();
        model.addAttribute("chefs", chefs);

        return "chefList";
    }

    @GetMapping("/chef-form/{id}")
    public String getEditChefForm(@PathVariable Long id, Model model) {
        Chef chef = chefService.findById(id);
        if(chef == null) {
            return "redirect:/chefs?error=ChefNotFound";
        }

        model.addAttribute("chef", chef);

        return "chef-form";
    }

    @GetMapping("/chef-form")
    public String getAddChefForm(Model model) {
        model.addAttribute("chef", new Chef());

        return "chef-form";
    }

    @PostMapping
    public String saveCHef(@RequestParam Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String bio, @RequestParam String gender) {
        try {
            chefService.create(id, firstName, lastName, bio, gender);
        } catch (IllegalArgumentException e){
            return "redirect:/chefs?error=" + e.getMessage();
        }
        return "redirect:/chefs";
    }

    @PostMapping("/edit/{id}")
    public String editChef(@PathVariable Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String bio, @RequestParam String gender) {
        try {
            chefService.update(id, firstName, lastName, bio, gender);
        } catch (RuntimeException e) {
            return "redirect:/chefs?error=" + e.getMessage();
        }
        return "redirect:/chefs";
    }

    @PostMapping("/delete/{id}")
    public String deleteChef(@PathVariable Long id) {
        try {
            chefService.delete(id);
        } catch (RuntimeException e) {
            return  "redirect:/chefs?error=" + e.getMessage();
        }

        return "redirect:/chefs";
    }
}
