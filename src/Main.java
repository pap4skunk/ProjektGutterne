import jdk.swing.interop.SwingInterOpUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main (String[] args){


        Scanner Bestilling = new Scanner(System.in);
        initMenu();
        loadOrdersFromFile();

        boolean running = true;

        while (running) {
            System.out.println("\n1. Tilføj bestilling");
            System.out.println("2. Fjern afhentet bestilling");
            System.out.println("3. Afslut");
            System.out.println();
            System.out.println();
        }

        String pizza = Bestilling.nextLine();


    }
}
