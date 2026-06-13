package pl.pizzaman.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.pizzaman.pizzeria.model.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

}
