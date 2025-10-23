import java.io.*;
import java.util.*;

class PizzaOrder {
    String pizzaName;
    String pickupTime;

    public PizzaOrder(String pizzaName, String pickupTime) {
        this.pizzaName = pizzaName;
        this.pickupTime = pickupTime;
    }

    @Override
    public String toString() {
        return "Pizza: " + pizzaName + " | Klar til: " + pickupTime;
    }
}

public class PizzaMenu {
    static ArrayList<String> pizzaMenu = new ArrayList<>();
    static ArrayList<PizzaOrder> orders = new ArrayList<>();
    static final String ORDER_FILE = "bestillinger.txt";
    static final String SOLD_FILE = "solgte_pizzaer.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initMenu();
        loadOrdersFromFile();

        boolean running = true;
        while (running) {
            System.out.println("\n1. Tilføj bestilling");
            System.out.println("2. Fjern afhentet bestilling");
            System.out.println("3. Vis statistik over solgte pizzaer");
            System.out.println("4. Afslut");
            System.out.print("\nVælg: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addOrder(scanner);
                case 2 -> removeOrder(scanner);
                case 3 -> showStatistics();
                case 4 -> running = false;
                default -> System.out.println("Ugyldigt valg.");
            }
        }

        scanner.close();
    }

    static void initMenu() {
        pizzaMenu.addAll(List.of(
                "Margherita", "Pepperoni", "Hawaii", "Vegetar", "Kebab", "Skinke og ost",
                "BBQ Chicken", "Mexicana", "Vesuvio", "Capricciosa", "Tuna Special",
                "Meat Lovers", "Spinaci", "Diavola"
        ));
    }

    static void loadOrdersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\| Klar til: ");
                if (parts.length == 2) {
                    String pizzaName = parts[0].replace("Pizza: ", "").trim();
                    String pickupTime = parts[1].trim();
                    orders.add(new PizzaOrder(pizzaName, pickupTime));
                }
            }
        } catch (IOException e) {
            System.out.println("Ingen tidligere bestillinger fundet.");
        }
    }

    static void addOrder(Scanner scanner) {
        System.out.println("\n--- PIZZAMENU ---");
        for (int i = 0; i < pizzaMenu.size(); i++) {
            System.out.println((i + 1) + ". " + pizzaMenu.get(i));
        }

        System.out.print("Vælg pizza (1-14): ");
        int pizzaChoice = scanner.nextInt();
        scanner.nextLine();

        if (pizzaChoice < 1 || pizzaChoice > 14) {
            System.out.println("Ugyldigt valg.");
            return;
        }

        String chosenPizza = pizzaMenu.get(pizzaChoice - 1);
        System.out.print("Tidspunkt for afhentning (fx 18.30): ");
        String time = scanner.nextLine();

        PizzaOrder order = new PizzaOrder(chosenPizza, time);
        orders.add(order);
        appendToFile(ORDER_FILE, order.toString());

        System.out.println("Bestilling tilføjet.");
    }

    static void removeOrder(Scanner scanner) {
        if (orders.isEmpty()) {
            System.out.println("Ingen aktive bestillinger.");
            return;
        }

        System.out.println("\n--- AKTIVE BESTILLINGER ---");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println((i + 1) + ". " + orders.get(i));
        }

        System.out.print("Vælg nummer på afhentet bestilling: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index < 1 || index > orders.size()) {
            System.out.println("Ugyldigt valg.");
            return;
        }

        PizzaOrder removed = orders.remove(index - 1);
        appendToFile(SOLD_FILE, removed.pizzaName);
        overwriteOrderFile();

        System.out.println("Bestilling fjernet og registreret som solgt.");
    }

    static void showStatistics() {
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
        if (stats.isEmpty()) {
            System.out.println("Ingen pizzaer solgt endnu.");
        } else {
            stats.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .forEach(entry -> System.out.printf("%s: %d stk\n", entry.getKey(), entry.getValue()));
        }
    }

    static void appendToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(content + "\n");
        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til fil: " + filename);
        }
    }

    static void overwriteOrderFile() {
        try (FileWriter writer = new FileWriter(ORDER_FILE, false)) {
            for (PizzaOrder order : orders) {
                writer.write(order.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Fejl ved opdatering af bestillingsfil.");
        }
    }
}