import java.io.*;

public class FileHandler {
    public static void writeToFile(String order) {
        String bestillingerFile = "bestillinger.txt";

        try (FileWriter writer = new FileWriter(bestillingerFile, true)) {
            int totalSold = 1;
            String pizza = "Pepperoni";
            int price = 53;
            writer.write(order + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readOrderFromFile() {
        String komma = ",";
        String line = "";
        String regnskabFile = "regnskab.txt";
        String name = null;
        int price = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(regnskabFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(komma);
                name = data[0];
                price = Integer.parseInt(data[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return name + "," + price;
    }

    public static void overwriteFile(String orders) {
        String regnskabFile = "regnskab.txt";

        try (FileWriter writer = new FileWriter(regnskabFile, true)) {
            writer.write(orders + "\n");
        } catch (IOException e) {
            System.out.println("Fejl ved opdatering af regnskabsfil.");
        }
    }
}
