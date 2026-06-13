package pl.pizzaman.pizzeria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.pizzaman.pizzeria.model.Topping;

@Repository
public interface ToppingRepository extends JpaRepository<Topping, Long> {

}
