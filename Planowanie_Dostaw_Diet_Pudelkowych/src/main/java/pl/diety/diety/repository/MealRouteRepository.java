package pl.diety.diety.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.diety.diety.model.MealRoute;

public interface MealRouteRepository extends JpaRepository<MealRoute, Long> {
}
