import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OdrerManager {

    private List<PizzaOrdrer> ord = new ArrayList<>();
    private final String ODREFIL = "Bestillinger.txt";
    private final String SOLGTFIL = "SolgtePizzaer.txt";

    public List<PizzaOrdrer> getOrd() {
        return ord;
    }

    public void loadOdre() {
        ord.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(ODREFIL))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", Klar til: ");
                if (parts.length >= 2) {
                    String pizzaNavn = parts[0].replace("Pizza: ", "").trim();
                    String afhentningsTid = parts[1].split(",")[0].trim();
                    List<String> ekstra = new ArrayList<>();
                    if (line.contains("Ekstra:")) {
                        String[] ekstraTing = line.split("Ekstra: ");
                        if (ekstraTing.length > 1) {
                            String[] ingredienser = ekstraTing[1].split(",")[0].split(". ");
                            for (String ing : ingredienser) {
                                if (!ing.equals("Ingen")) ekstra.add(ing.trim());
                            }
                        }
                    }
                    ord.add(new PizzaOrdrer(new Pizza(pizzaNavn, 0), afhentningsTid, ekstra));
                }
            }
        }
        catch (IOException e) {
            System.out.println("Ingen tidligere bestillinger fundet.");
        }
    }

    public void tilfojOrdre(PizzaOrdrer ordrer) {
        ord.add(ordrer);
        FileUtil.tilfojTilFil(ODREFIL, ordrer.toString());
    }

    public void fjernOrdre(int index) {
        PizzaOrdrer fjernet = ord.remove(index);
        String solgt = fjernet.getPizzaNavn() + "," + fjernet.udregnPris();
        FileUtil.tilfojTilFil(SOLGTFIL, solgt);
        FileUtil.overskrivFil(ODREFIL, ord);
    }

    public void redigerIngredienser(int index, String toFjern) {
        PizzaOrdrer ordrer = ord.get(index);
        if (ordrer.getEkstraIngrediens().remove(toFjern)) {
            System.out.println("Ingrediens fjernet.");
            FileUtil.overskrivFil(ODREFIL, ord);
        } else {
            System.out.println("Ingrediens ikke fundet.");
        }
    }

    public void visSalg() {
        Map<String, Integer> salgsTal = new HashMap<>();
        Map<String, Integer> indtjeningPrPizza = new HashMap<>();
        int totalIndkomst = 0;
        int totalSalg = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(SOLGTFIL))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String [] parts = line.split(",");
                String pizzaNavn = parts[0];
                int pris = parts.length > 1 ? Integer.parseInt(parts[1]) : 0;
                salgsTal.put(pizzaNavn, salgsTal.getOrDefault(pizzaNavn, 0) + 1);
                indtjeningPrPizza.put(pizzaNavn, indtjeningPrPizza.getOrDefault(pizzaNavn, 0) + pris);
                totalIndkomst += pris;
                totalSalg++;
            }
        } catch (IOException e) {
            System.out.println("Ingen solgte pizzaer fundet.");
            return;
        }

        if (salgsTal.isEmpty()) {
            System.out.println("Ingen solgte pizzaer endnu");
            return;
        }

        String bestSeller = null;
        int maxSolgte = 0;

        System.out.println("\n  STATISTIK OVER SOLGTE PIZZAER  ");
        for(String pizza : salgsTal.keySet()) {
            int tael = salgsTal.get(pizza);
            int indtjening = indtjeningPrPizza.get(pizza);
            System.out.printf("%s solgt %d stk, omsætning %d kr\n", pizza, tael, indtjening);
            if (tael > maxSolgte) {
                maxSolgte = tael;
                bestSeller = pizza;
            }
        }

        System.out.println("\nTotal omsætning: " + totalIndkomst + " kr");
        System.out.println("Bedst sælgende pizza: " + bestSeller + " (" + maxSolgte + " stk)");
        System.out.println("Antal pizzaer solgt: " + totalSalg);
    }
}

