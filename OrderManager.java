import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders = new ArrayList<>();
    private List<Order> sellers = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public List<Order> getSellers() {
        return sellers;
    }
    public void addOrder(Order order) {
        orders.add(order);
        FileHandler.writeToFile(order.getPizzaName() + "," +  order.getPickupTime() + "," + order.getExtraIngredients() + "," + order.calculatePrice());
    }

    public void addSeller(Order order) {
        sellers.add(order);
    }

    public void sellOrder(int index) {
        Order removed = orders.remove(index);
        String soldLine = removed.getPizzaName() + "," + removed.calculatePrice();
        FileHandler.overwriteFile(soldLine);
    }

    public int calculateTotalPrice() {
        int total = 0;
        for (int i = 0; i < sellers.size(); i++) {
            int price = sellers.get(i).calculatePrice();
            total += price;
        }
        return total;
    }
}
