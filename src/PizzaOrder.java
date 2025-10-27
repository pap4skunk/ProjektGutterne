import java.util.*;

public class PizzaOrder {
    private Pizza pizza;
    private String pickupTime;
    private List<String> extraIngredients;

    public PizzaOrder(Pizza pizza, String pickupTime, List<String> extraIngredients) {
        this.pizza = pizza;
        this.pickupTime = pickupTime;
        this.extraIngredients = new ArrayList<>(extraIngredients);
    }

    public Pizza getPizza() { return pizza; }
    public String getPickupTime() { return pickupTime; }
    public List<String> getExtraIngredients() { return extraIngredients; }

    public int calculatePrice() {
        return pizza.getPris() + (extraIngredients.size() * 5);
    }

    @Override
    public String toString() {
        String extras = extraIngredients.isEmpty() ? "Ingen" : String.join(", ", extraIngredients);
        return String.format("Pizza: %s | Klar til: %s | Ekstra: %s | Pris: %d kr",
                pizza.getNavn(), pickupTime, extras, calculatePrice());
    }

    public String getPizzaName() {
        return pizza.getNavn();
    }
}

