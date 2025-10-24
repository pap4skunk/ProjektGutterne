import java.util.*;
import java.io.*;

public class OrderManager {
    private List<PizzaOrder> orders = new ArrayList<>();
    private final String ORDER_FILE = "bestillinger.txt";
    private final String SOLD_FILE = "solgte_pizzaer.txt";

    public List<PizzaOrder> getOrders() {
        return orders;
    }

    public void loadOrders() {
        orders.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\| Klar til: ");
                if (parts.length >= 2) {
                    String pizzaName = parts[0].replace("Pizza: ", "").trim();
                    String pickupTime = parts[1].split("\\|")[0].trim();
                    List<String> extras = new ArrayList<>();
                    if (line.contains("Ekstra:")) {
                        String[] extraParts = line.split("Ekstra: ");
                        if (extraParts.length > 1) {
                            String[] ingredients = extraParts[1].split("\\|")[0].split(", ");
                            for (String ing : ingredients) {
                                if (!ing.equals("Ingen")) extras.add(ing.trim());
                            }
                        }
                    }
                    // Pris læses ikke fra filen, da den beregnes automatisk
                    orders.add(new PizzaOrder(new Pizza(pizzaName, 0), pickupTime, extras));
                }
            }
        } catch (IOException e) {
            System.out.println("Ingen tidligere bestillinger fundet.");
        }
    }

    public void addOrder(PizzaOrder order) {
        orders.add(order);
        FileHandler.appendToFile(ORDER_FILE, order.toString());
    }

    public void removeOrder(int index) {
        PizzaOrder removed = orders.remove(index);
        FileHandler.appendToFile(SOLD_FILE, removed.getPizzaName());
        FileHandler.overwriteFile(ORDER_FILE, orders);
    }

    public void editOrderIngredients(int index, String toRemove) {
        PizzaOrder order = orders.get(index);
        if (order.getExtraIngredients().remove(toRemove)) {
            System.out.println("Ingrediens fjernet.");
            FileHandler.overwriteFile(ORDER_FILE, orders);
        } else {
            System.out.println("Ingrediens ikke fundet.");
        }
    }

    public void showStatistics(List<Pizza> menu) {
        Map<String, Integer> stats = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SOLD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stats.put(line, stats.getOrDefault(line, 0) + 1);
            }
        } catch (IOException e) {
            System.out.println("Fejl ved læsning af statistikfil.");
            return;
        }

        System.out.println("\n--- STATISTIK OVER SOLGTE PIZZAER ---");
        for (int i = 0; i < menu.size(); i++) {
            String pizza = menu.get(i).getNavn();
            int count = stats.getOrDefault(pizza, 0);
            System.out.printf("%2d. %-15s solgt: %d stk.\n", i + 1, pizza, count);
        }
    }
}
