import java.util.*;

public class PizzaMenu {
    private List<String> menu;

    public PizzaMenu() {
        menu = new ArrayList<>(List.of(
                "Margherita", "Pepperoni", "Hawaii", "Vegetar", "Kebab", "Skinke og ost",
                "BBQ Chicken", "Mexicana", "Vesuvio", "Capricciosa", "Tuna Special",
                "Meat Lovers", "Spinaci", "Diavola"
        ));
    }

    public List<String> getMenu() {
        return menu;
    }

    public String getPizza(int index) {
        if (index >= 0 && index < menu.size()) {
            return menu.get(index);
        }
        return null;
    }

    public void printMenu() {
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }
}