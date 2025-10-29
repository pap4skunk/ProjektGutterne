import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        OrderManager manager = new OrderManager();
        FileHandler handler = new FileHandler();
        Menu menu = new Menu();

        boolean running = true;
        while (running) {
            System.out.println("--- PIZZA BESTILLING SYSTEM ---");
            System.out.println("1. Tilføj bestilling");
            System.out.println("2. Fjern afhentet bestilling");
            System.out.println("3. Vis statistik over solgte pizzaer");
            System.out.println("4. Afslut");
            System.out.print("\nVælg: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ugyldigt valg. Skriv et tal mellem 1 - 5.");
            }

            switch (choice) {
                case 1:
                    menu.printMenu();
                    System.out.print("Vælg pizza (1 - " + menu.getMenu().size() + "): ");
                    int pizzaChoice = Integer.parseInt(scanner.nextLine());
                    if (pizzaChoice < 1 || pizzaChoice > menu.getMenu().size()) {
                        System.out.println("Ugyldigt valg.");
                        break;
                    }

                    Pizza chosenPizza = menu.getPizza(pizzaChoice - 1);
                    System.out.print("Tidspunkt for afhentning (fx 18.30): ");
                    String time = scanner.nextLine();

                    List<String> extras = new ArrayList<>();
                    System.out.println("Tilføj ekstra ingredienser (skriv færdig for at afslutte):");
                    while (true) {
                        System.out.print("Ekstra ingrediens: ");
                        String input = scanner.nextLine().trim();
                        if (input.equalsIgnoreCase("færdig")) {
                            break;
                        }
                        if (!input.isEmpty()) {
                            extras.add(input);
                        }
                    }

                    Order order = new Order(chosenPizza, time, extras);
                    manager.addOrder(order);
                    manager.addSeller(order);
                    System.out.println("Bestilling tilføjet: " + order.getPizzaName() + "," + order.getPickupTime() + "," + order.getExtraIngredients() + "," + order.calculatePrice());
                    break;
                case 2:
                    List<Order> orders = manager.getOrders();
                    if (orders.isEmpty()) {
                        System.out.println("Ingen aktive bestillinger");
                        break;
                    }
                    for (int i = 0; i < orders.size(); i++) {
                        System.out.println((i + 1) + ". " + orders.get(i).getPizzaName());
                    }

                    System.out.print("Vælg nummer på afhentet bestilling: ");
                    int index = Integer.parseInt(scanner.nextLine()) - 1;
                    if (index < 0 || index >= orders.size()) {
                        System.out.println("Ugyldigt valg.");
                        break;
                    }
                    manager.sellOrder(index);
                    System.out.println("Bestilling er registreret som solgt");
                    break;
                case 3:
                    List<Order> sellers = manager.getSellers();
                    for (int i = 0; i < sellers.size(); i++) {
                        System.out.println(sellers.get(i).getPizzaName());;
                    }
                    System.out.println("\nTotal pizzaer solgt: " + sellers.size());

                    System.out.println("Total omsætning i kr: " + manager.calculateTotalPrice());
                    break;
                case 4:
                    running = false;
                    System.out.println("Programmet afsluttes...");
                    break;
                default:
                    System.out.println("Ugyldigt valg.");
                    break;

            }
        }
        scanner.close();
    }
}