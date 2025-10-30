import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileUtil {

    public static void tilfojTilFil(String filnavn, String indhold) {
        try (FileWriter writer = new FileWriter(filnavn, true)) {
            writer.write(indhold + "\n");
        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til fil: " + filnavn);
        }
    }

    public static void overskrivFil(String filnavn, List<PizzaOrdrer> ord) {
        try (FileWriter writer = new FileWriter(filnavn, false)) {
            for (PizzaOrdrer ordrer : ord) {
                writer.write(ordrer.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Fejl ved opdatering af bestillingsfil.");
        }
    }
}

