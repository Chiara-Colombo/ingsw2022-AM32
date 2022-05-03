package it.polimi.ingsw;

import it.polimi.ingsw.client.CLI;
import it.polimi.ingsw.server.Server;

import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main( String[] args ) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Menù\n\n 1 - Start Server\n 2 - Start CLI Client\n\n- ");
        int choice = sc.nextInt();
        if (choice == 1) {
            try {
                new Server().start();
            } catch (IOException e) {
                System.out.println("Error - Can't start the server");
            }
        } else if (choice == 2) {
            CLI client = new CLI();
            client.start();
        }
    }
}