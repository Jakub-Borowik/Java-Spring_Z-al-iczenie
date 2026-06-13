package pl.pizzaman.pizzeria.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.pizzaman.pizzeria.model.Pizza;
import pl.pizzaman.pizzeria.model.Topping;
import pl.pizzaman.pizzeria.repository.PizzaRepository;
import pl.pizzaman.pizzeria.repository.ToppingRepository;

@Service
@RequiredArgsConstructor
public class PizzaService {
    private final PizzaRepository pizzaRepository;
    private final ToppingRepository toppingRepository;

    public BigDecimal calculateCustomPizzaPrice(Pizza pizza) {
        return pizza.getToppings().stream()
                .map(Topping::getPrice)
                .reduce(pizza.getBasePrice(), BigDecimal::add);
    }

    public void orderCustomPizza(Pizza pizza) {
        BigDecimal calculatedPrice = calculateCustomPizzaPrice(pizza);
        pizza.setBasePrice(calculatedPrice);
        pizzaRepository.save(pizza);
    }
}
