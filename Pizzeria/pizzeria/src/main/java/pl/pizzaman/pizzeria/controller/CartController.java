package pl.pizzaman.pizzeria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import pl.pizzaman.pizzeria.model.Cart;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/pizzeria")
@RequiredArgsConstructor
public class CartController {
    private final Cart cart;

    @GetMapping("/koszyk")
    public String showCart(Model model) {
        model.addAttribute("items", cart.getItems());
        return "koszyk";
    }
    
    @DeleteMapping("/koszyk")
    public String removeItem(@RequestParam Long id) {
        cart.removePizza(id);
        return "redirect:/pizzeria/koszyk";
    }

    @PostMapping("/koszyk/zamow")
    public String checkout() {
        cart.clear();
        return "redirect:/pizzeria/menu";
    }
}
