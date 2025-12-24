package mk.ukim.finki.wp.lab.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Admin: " + encoder.encode("admin"));
        System.out.println("User: " + encoder.encode("user"));
    }
}
