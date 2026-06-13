package pl.pizzaman.pizzeria.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Cart {
    private List<OrderItem> items = new ArrayList<>();

    public void addPizza(Pizza pizza) {
        OrderItem item = new OrderItem();
        item.setPizza(pizza);
        item.setQuantity(1);
        items.add(item);
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public void removePizza(Long id) {
        items.removeIf(item -> item.getPizza().getId().equals(id));
    }
}
