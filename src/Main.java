import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PizzaMenu menu = new PizzaMenu();
        OrderManager manager = new OrderManager();
        manager.loadOrders();

        boolean running = true;
        while (running) {
            System.out.println("\n1. Tilføj bestilling");
            System.out.println("2. Fjern afhentet bestilling");
            System.out.println("3. Vis statistik over solgte pizzaer");
            System.out.println("4. Rediger ekstra ingredienser");
            System.out.println("5. Afslut");
            System.out.print("\nVælg: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    menu.printMenu();
                    System.out.print("Vælg pizza (1-14): ");
                    int pizzaChoice = scanner.nextInt();
                    scanner.nextLine();
                    if (pizzaChoice < 1 || pizzaChoice > menu.getMenu().size()) {
                        System.out.println("Ugyldigt valg.");
                        break;
                    }
                    String chosenPizza = menu.getPizza(pizzaChoice - 1);
                    System.out.print("Tidspunkt for afhentning (fx 18.30): ");
                    String time = scanner.nextLine();

                    List<String> extras = new ArrayList<>();
                    System.out.println("Tilføj ekstra ingredienser (skriv 'færdig' for at afslutte):");
                    while (true) {
                        System.out.print("Ekstra ingrediens: ");
                        String input = scanner.nextLine().trim();
                        if (input.equalsIgnoreCase("færdig")) break;
                        if (!input.isEmpty()) extras.add(input);
                    }

                    PizzaOrder order = new PizzaOrder(chosenPizza, time, extras);
                    manager.addOrder(order);
                    System.out.println("Bestilling tilføjet.");
                }
                case 2 -> {
                    List<PizzaOrder> orders = manager.getOrders();
                    if (orders.isEmpty()) {
                        System.out.println("Ingen aktive bestillinger.");
                        break;
                    }
                    for (int i = 0; i < orders.size(); i++) {
                        System.out.println((i + 1) + ". " + orders.get(i));
                    }
                    System.out.print("Vælg nummer på afhentet bestilling: ");
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    if (index < 1 || index > orders.size()) {
                        System.out.println("Ugyldigt valg.");
                        break;
                    }
                    manager.removeOrder(index - 1);
                    System.out.println("Bestilling fjernet og registreret som solgt.");
                }

            }
        }
    }
}