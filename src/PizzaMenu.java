import java.util.*;

public class PizzaMenu {
    private List<Pizza> menu;

    public PizzaMenu() {
        menu = new ArrayList<>(List.of(
                new Pizza("Margherita", 75),
                new Pizza("Pepperoni", 85),
                new Pizza("Hawaii", 80),
                new Pizza("Vegetar", 79),
                new Pizza("Kebab", 85),
                new Pizza("Skinke og ost", 80),
                new Pizza("BBQ Chicken", 90),
                new Pizza("Mexicana", 89),
                new Pizza("Vesuvio", 84),
                new Pizza("Capricciosa", 86),
                new Pizza("Tuna Special", 82),
                new Pizza("Meat Lovers", 95),
                new Pizza("Spinaci", 78),
                new Pizza("Diavola", 88)
        ));
    }

    public List<Pizza> getMenu() { return menu; }

    public Pizza getPizza(int index) {
        if (index >= 0 && index < menu.size()) return menu.get(index);
        return null;
    }

    public void printMenu() {
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }
}
