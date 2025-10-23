import java.util.*;

public class PizzaOrder {
    private String pizzaName;
    private String pickupTime;
    private List<String> extraIngredients;

    public PizzaOrder(String pizzaName, String pickupTime, List<String> extraIngredients) {
        this.pizzaName = pizzaName;
        this.pickupTime = pickupTime;
        this.extraIngredients = new ArrayList<>(extraIngredients);
    }

    public String getPizzaName() { return pizzaName; }
    public String getPickupTime() { return pickupTime; }
    public List<String> getExtraIngredients() { return extraIngredients; }

    public int calculatePrice() {
        return 75 + (extraIngredients.size() * 5);
    }

    @Override
    public String toString() {
        String extras = extraIngredients.isEmpty() ? "Ingen" : String.join(", ", extraIngredients);
        return String.format("Pizza: %s | Klar til: %s | Ekstra: %s | Pris: %d kr",
                pizzaName, pickupTime, extras, calculatePrice());
    }
}