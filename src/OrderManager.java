import java.io.*;
import java.util.*;

public class OrderManager {
    private List<PizzaOrder> orders = new ArrayList<>();
    private final String ORDER_FILE = "bestillinger.txt";
    private final String SOLD_FILE = "solgte_pizzaer.txt";

    public List<PizzaOrder> getOrders() { return orders; }

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
        String soldLine = removed.getPizzaName() + "|" + removed.calculatePrice();
        FileHandler.appendToFile(SOLD_FILE, soldLine);
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

    public void showSalesSummary() {
        Map<String, Integer> salesCount = new HashMap<>();
        Map<String, Integer> revenuePerPizza = new HashMap<>();
        int totalRevenue = 0;
        int totalSold = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(SOLD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String pizzaName = parts[0];
                int price = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
                salesCount.put(pizzaName, salesCount.getOrDefault(pizzaName, 0) + 1);
                revenuePerPizza.put(pizzaName, revenuePerPizza.getOrDefault(pizzaName, 0) + price);
                totalRevenue += price;
                totalSold++;
            }
        } catch (IOException e) {
            System.out.println("Ingen solgte pizzaer fundet.");
            return;
        }

        if (salesCount.isEmpty()) {
            System.out.println("Ingen solgte pizzaer endnu.");
            return;
        }

        String bestSeller = null;
        int maxSold = 0;

        System.out.println("\n--- STATISTIK OVER SOLGTE PIZZAER ---");
        for (String pizza : salesCount.keySet()) {
            int count = salesCount.get(pizza);
            int revenue = revenuePerPizza.get(pizza);
            System.out.printf("%s: solgt %d stk, omsætning %d kr\n", pizza, count, revenue);
            if (count > maxSold) {
                maxSold = count;
                bestSeller = pizza;
            }
        }

        System.out.println("\nTotal omsætning: " + totalRevenue + " kr");
        System.out.println("Bedst sælgende pizza: " + bestSeller + " (" + maxSold + " stk)");
        System.out.println("Antal pizzaer solgt: " + totalSold);
    }
}
