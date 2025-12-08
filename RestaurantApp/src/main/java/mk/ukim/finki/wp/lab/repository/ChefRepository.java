package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Chef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChefRepository extends JpaRepository<Chef, Long> {
//    List<Chef> findAll();
//    Optional<Chef> findById(Long id);
//    Chef save(Chef chef);
//    void deleteById(Long id);
}
