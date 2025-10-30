import java.util.ArrayList;
import java.util.List;

public class PizzaMenu {


    private List<Pizza> menu;

    public PizzaMenu() {
        menu = new ArrayList<>(List.of(
                new Pizza("Vesuvio:", 57),
                new Pizza("Amerikaner:", 53),
                new Pizza("Cacciatore:", 57),
                new Pizza("Carbona:", 63),
                new Pizza("Dennis:", 65),
                new Pizza("Bertil:", 57),
                new Pizza("Silvia:", 61),
                new Pizza("Victoria:", 61),
                new Pizza("Toronfo:", 61),
                new Pizza("Capricciosa:", 61),
                new Pizza("Hawaii:", 61),
                new Pizza("Le Blissola:", 61),
                new Pizza("Venezia:", 61),
                new Pizza("Mafia:", 61)
        ));
    }

    public List<Pizza> getMenu() {
        return menu;
    }

    public Pizza getPizza(int index) {
        if (index >= 0 && index < menu.size()) return menu.get(index);
        return null;
    }

    public void printMenu() {
        for(int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }
    }
}
