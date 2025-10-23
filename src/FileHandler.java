import java.io.*;
import java.util.*;

public class FileHandler {
    public static void appendToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(content + "\n");
        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til fil: " + filename);
        }
    }

    public static void overwriteFile(String filename, List<PizzaOrder> orders) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            for (PizzaOrder order : orders) {
                writer.write(order.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Fejl ved opdatering af bestillingsfil.");
        }
    }
}