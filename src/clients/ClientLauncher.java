package clients;

import java.util.Scanner;

/**
 * The main client for running the Player and Setup clients.
 */
public class ClientLauncher {
    public static void main(String[] args) {
        ClientLauncher main = new ClientLauncher();
        main.launch();
    }

    public void launch() {
        printHeader();

        System.out.println("\n");       //spacer
        for (int i = 0; i < 60; i++) {
            System.out.print("=");      //horizontal rule
        }

        System.out.println("What do you want to do?");
        System.out.println("1. Create or edit a quiz");
        System.out.println("2. Play a quiz");
        Scanner input = new Scanner(System.in);
        int choice = Integer.parseInt(input.nextLine());

        switch (choice) {
            case 1:
                startSetupClient();
                break;
            case 2:
                startPlayerClient();
                break;
            default:
                System.out.println("Invalid choice. Please choose an option.");
                break;
        }
    }

    public void startSetupClient(){
        SetupClient setup = new SetupClient();
        setup.execute();
    }

    public void startPlayerClient(){
        PlayerClient player = new PlayerClient();
        player.execute();
    }

    public void printHeader(){
        for (int i=0; i<60; i++) {
            System.out.print("*");  //top border
        }
        for (int i=0; i<2; i++) {
            System.out.print("\n*");    // top
            for (int j=0; j<58; j++){
                System.out.print(" ");
            }
            System.out.print("*");
        }
        System.out.print("\n*");
        for (int j=0; j<24; j++){
            System.out.print(" ");
        }
        System.out.print("QUIZ  GAME"); //middle

        for (int j=0; j<24; j++){
            System.out.print(" ");
        }
        System.out.print("*");
        for (int i=0; i<2; i++) {
            System.out.print("\n*");
            for (int j=0; j<58; j++){
                System.out.print(" ");  //bottom
            }
            System.out.print("*");
        }
        System.out.print("\n");
        for (int i=0; i<60; i++) {
            System.out.print("*");      //bottom border
        }
        System.out.println("\n");       //spacer
        for (int i=0; i<60; i++){
            System.out.print("=");      //horizontal rule
        }
    }
}
