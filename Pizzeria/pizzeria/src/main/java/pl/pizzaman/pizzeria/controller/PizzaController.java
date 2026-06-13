package pl.pizzaman.pizzeria.controller;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import pl.pizzaman.pizzeria.model.Cart;
import pl.pizzaman.pizzeria.model.Pizza;
import pl.pizzaman.pizzeria.repository.PizzaRepository;
import pl.pizzaman.pizzeria.repository.ToppingRepository;
import pl.pizzaman.pizzeria.service.PizzaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/pizzeria")
@RequiredArgsConstructor
public class PizzaController {
    private final PizzaRepository pizzaRepository;
    private final PizzaService pizzaService;
    private final ToppingRepository toppingRepository;
    private final Cart cart;

    @GetMapping("/menu")
    public String showMenu(Model model) {
        model.addAttribute("pizzas", pizzaRepository.findAll());
        return "menu";
    }
    

    @GetMapping("/kreator")
    public String showPizzaCreator(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("allToppings", toppingRepository.findAll());
        return "kreator";
    }

    @PostMapping("/kreator")
    public String processPizzaOrder(@ModelAttribute Pizza pizza) {
        pizza.setBasePrice(new BigDecimal("15.00"));
        pizzaService.orderCustomPizza(pizza);
        return "redirect:/pizzeria/menu";
    }

    @PostMapping("/koszyk")
    public String addToCart(@RequestParam Long id) {
        Pizza selectedPizza = pizzaRepository.findById(id).orElseThrow();
        cart.addPizza(selectedPizza);
        return "redirect:/pizzeria/menu";
    }
}
