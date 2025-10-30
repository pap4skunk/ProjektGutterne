import java.util.ArrayList;
import java.util.List;

public class PizzaOrdrer {
    private Pizza pizza;
    private String afhentningstid;
    private List<String> ekstraIngrediens;

    public PizzaOrdrer(Pizza pizza, String afhentningstid, List<String> ekstraIngrediens) {
        this.pizza = pizza;
        this.afhentningstid = afhentningstid;
        this.ekstraIngrediens = new ArrayList<>(ekstraIngrediens);
    }

    public Pizza getPizza() {
        return pizza;
    }
    public String getAfhentningstid() {
        return afhentningstid;
    }
    public List<String> getEkstraIngrediens() {
        return ekstraIngrediens;
    }

    public int udregnPris() {
        return pizza.getPris() + (ekstraIngrediens.size() * 5);
    }

    @Override
    public  String toString() {
        String ekstra = ekstraIngrediens.isEmpty() ? "Ingen" : String.join(", ", ekstraIngrediens);
        return String.format("Pizza: %s , Klar til: %s , Ekstra: %s , Pris: %d kr",
                pizza.getNavn(), afhentningstid, ekstra, udregnPris());
    }

    public String getPizzaNavn() {
        return pizza.getNavn();
    }
}
