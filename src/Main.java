import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PizzaMenu menu = new PizzaMenu();
        OdrerManager manager = new OdrerManager();
        manager.loadOdre();

        boolean go = true;
        while (go) {
            System.out.println("\n  PIZZA BESTILLINGS SYSTEM  ");
            System.out.println("1. Tilføj bestilling");
            System.out.println("2. Fjern afhentet bestilling");
            System.out.println("3. Vis statistik over solgte pizzaer");
            System.out.println("4. Rediger ekstra ingredienser");
            System.out.println("5. Afslut");
            System.out.print("\nVælg: ");


            int valg;
            try {
                valg = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Ugyldigt valg. Skriv et tal mellem 1-5.");
                continue;
            }

            switch (valg) {
                case 1 -> {
                    menu.printMenu();
                    System.out.print("Vælg pizza (1-" + menu.getMenu().size() + "): ");
                    int pizzaValg = Integer.parseInt(scanner.nextLine());
                    if (pizzaValg < 1 || pizzaValg > menu.getMenu().size()) {
                        System.out.println("Ugyldigt valg.");
                        break;
                    }

                    Pizza valgtPizza = menu.getPizza(pizzaValg - 1);
                    System.out.print("Tidspunkt for afhetning: ");
                    String tid = scanner.nextLine();

                    List<String> ekstra = new ArrayList<>();
                    System.out.println("Tilføj ekstra ingredienser (tast '0' for at afslutte):");
                    while (true) {
                        System.out.print("Ekstra ingrediens: ");
                        String tast = scanner.nextLine().trim();
                        if (tast.equalsIgnoreCase("0")) break;
                        if (!tast.isEmpty()) ekstra.add(tast);
                    }

                    PizzaOrdrer ordrer = new PizzaOrdrer(valgtPizza, tid, ekstra);
                    manager.tilfojOrdre(ordrer);
                    System.out.println("Bestilling tilføjet: " + ordrer);
                }

                case 2 -> {
                    List<PizzaOrdrer> ord = manager.getOrd();
                    if (ord.isEmpty()) {
                        System.out.println("Ingen aktive bestillinger.");
                        break;
                    }
                    for (int i = 0; i <ord.size(); i++) {
                        System.out.println((i + 1) +". " + ord.get(i));
                    }
                    System.out.print("Vælg nummer på afhentet bestilling: ");
                    int index = Integer.parseInt(scanner.nextLine()) - 1;
                    if (index < 0 || index >= ord.size()) {
                        System.out.println("Ugyldigt valg.");
                        break;
                    }
                    manager.fjernOrdre(index);
                    System.out.println("Bestilling fjernet og tilføjet til solgte.");
                }

                case 3 -> manager.visSalg();

                case 4 -> {
                    List<PizzaOrdrer> ord = manager.getOrd();
                    if (ord.isEmpty()) {
                        System.out.println("Ingen aktive bestillinger.");
                        break;
                    }
                    for (int i = 0; i < ord.size(); i++) {
                        System.out.println((i + 1) + ". " + ord.get(i));
                    }
                    System.out.print("Vælg bestilling: ");
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx < 0 || idx >= ord.size()) {
                        System.out.println("Ugyldigt valg.");
                        break;
                    }
                    System.out.print("Indtast ingrediens der skal fjernes: ");
                    String ingrediens = scanner.nextLine();
                    manager.redigerIngredienser(idx, ingrediens);
                }

                case 5 -> {
                    go = false;
                    System.out.println("Programmet afsluttes");
                }

                default -> System.out.println("Ugyldigt valg");


            }
        }
        scanner.close();
    }
}
