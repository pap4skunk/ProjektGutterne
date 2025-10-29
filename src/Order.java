import java.util.ArrayList;
import java.util.List;

public class Order {
    private Pizza pizza;
    private String pickupTime;
    private List<String> extraIngredients;

    public Order(Pizza enPizza, String etPickupTime, List<String> enExtraIngredients) {
        pizza = enPizza;
        pickupTime = etPickupTime;
        extraIngredients = new ArrayList<>(enExtraIngredients);
    }

    public Pizza getPizza() {
        return pizza;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public List<String> getExtraIngredients() {
        return extraIngredients;
    }

    public int calculatePrice() {
        return pizza.getPris() + (extraIngredients.size() * 5);
    }

    public String getPizzaName() {
        return pizza.getNavn();
    }
}
