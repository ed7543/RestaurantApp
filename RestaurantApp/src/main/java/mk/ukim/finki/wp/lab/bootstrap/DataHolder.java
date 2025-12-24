package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.model.enums.Role;
import mk.ukim.finki.wp.lab.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Chef> chefs = new ArrayList<>();
    public static List<Dish> dishes = new ArrayList<>();
    public List<User> users = new ArrayList<>();
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataHolder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {

        if (userRepository.findAll().isEmpty()) {
            users = new ArrayList<>();
            users.add(new User("elena.atanasoska", passwordEncoder.encode("ea"), "Elena", "Atanasoska", Role.ROLE_USER));
            users.add(new User("darko.sasanski", passwordEncoder.encode("ds"), "Darko", "Sasanski", Role.ROLE_USER));
            users.add(new User("ana.todorovska", passwordEncoder.encode("at"), "Ana", "Todorovska", Role.ROLE_USER));
            users.add(new User("admin", passwordEncoder.encode("admin"), "admin", "admin", Role.ROLE_ADMIN));
            userRepository.saveAll(users);
        }


        dishes = new ArrayList<>();

        dishes.add(new Dish(1L, "D1", "Pasta Carbonara", "Italian", 25));
        dishes.add(new Dish(2L,"D2", "Sushi Roll", "Japanese", 40));
        dishes.add(new Dish(3L,"D3", "Tacos al Pastor", "Mexican", 30));
        dishes.add(new Dish(4L,"D4", "Butter Chicken", "Indian", 50));
        dishes.add(new Dish(5L,"D5", "French Onion Soup", "French", 60));

        chefs = new ArrayList<>();

        chefs.add(new Chef(1L, "Gordon", "Ramsey", "Hell's Kitchen chef", List.of(dishes.get(0), dishes.get(4)), "Male"));
        chefs.add(new Chef(2L, "Jamie", "Oliver", "Healthy cooking master", List.of(dishes.get(1)), "Male"));
        chefs.add(new Chef(3L, "Anthony", "Bourdain", "World traveler and chef", List.of(dishes.get(2), dishes.get(3)), "Male"));
        chefs.add(new Chef(4L, "Massimo", "Bottura", "Italian fine-dining chef",  List.of(dishes.get(0)), "Male"));
        chefs.add(new Chef(5L, "Nigella", "Lawson", "Home cooking queen", List.of(dishes.get(4)), "Male"));



    }
}
