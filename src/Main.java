import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PizzaMenu menu = new PizzaMenu();
        OrderManager manager = new OrderManager();
        manager.loadOrders();

        boolean running = true;
        while (running) {
            System.out.println("\n--- PIZZA BESTILLING SYSTEM ---");
            System.out.println("1. Tilføj bestilling");
            System.out.println("2. Fjern afhentet bestilling");
            System.out.println("3. Vis statistik over solgte pizzaer");
            System.out.println("4. Rediger ekstra ingredienser");
            System.out.println("5. Afslut");
            System.out.print("\nVælg: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ugyldigt valg. Skriv et tal mellem 1-5.");
                continue;
            }

            switch (choice) {
                case 1 -> { // Tilføj bestilling
                    menu.printMenu();
                    System.out.print("Vælg pizza (1-" + menu.getMenu().size() + "): ");
                    int pizzaChoice = Integer.parseInt(scanner.nextLine());
                    if (pizzaChoice < 1 || pizzaChoice > menu.getMenu().size()) {
                        System.out.println("Ugyldigt valg.");
                        break;
                    }

                    Pizza chosenPizza = menu.getPizza(pizzaChoice - 1);
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
                    System.out.println("Bestilling tilføjet: " + order);
                }

                case 2 -> { // Fjern afhentet bestilling
                    List<PizzaOrder> orders = manager.getOrders();
                    if (orders.isEmpty()) {
                        System.out.println("Ingen aktive bestillinger.");
                        break;
                    }
                    for (int i = 0; i < orders.size(); i++) {
                        System.out.println((i + 1) + ". " + orders.get(i));
                    }
                    System.out.print("Vælg nummer på afhentet bestilling: ");
                    int index = Integer.parseInt(scanner.nextLine()) - 1;
                    if (index < 0 || index >= orders.size()) {
                        System.out.println("Ugyldigt valg.");
                        break;
                    }
                    manager.removeOrder(index);
                    System.out.println("Bestilling fjernet og registreret som solgt.");
                }

                case 3 -> manager.showSalesSummary(); // Vis statistik

                case 4 -> { // Rediger ekstra ingredienser
                    List<PizzaOrder> orders = manager.getOrders();
                    if (orders.isEmpty()) {
                        System.out.println("Ingen aktive bestillinger.");
                        break;
                    }
                    for (int i = 0; i < orders.size(); i++) {
                        System.out.println((i + 1) + ". " + orders.get(i));
                    }
                    System.out.print("Vælg bestilling: ");
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx < 0 || idx >= orders.size()) {
                        System.out.println("Ugyldigt valg.");
                        break;
                    }
                    System.out.print("Indtast ingrediens der skal fjernes: ");
                    String ingredient = scanner.nextLine();
                    manager.editOrderIngredients(idx, ingredient);
                }

                case 5 -> { // Afslut program
                    running = false;
                    System.out.println("Programmet afsluttes...");
                }

                default -> System.out.println("Ugyldigt valg.");
            }
        }
        scanner.close();
    }
}
