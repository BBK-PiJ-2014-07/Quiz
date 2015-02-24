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
        boolean finished = false;
        while (!finished){
            System.out.println("\n");       //spacer
            for (int i = 0; i < 60; i++) {
                System.out.print("=");      //horizontal rule
            }
            Scanner input = new Scanner(System.in);
            boolean invalid;
            do {
                invalid = false;
                System.out.println("\nWhat do you want to do?");
                System.out.println("1. Create or edit a quiz");
                System.out.println("2. Play a quiz");
                System.out.println("3. Quit");

                int choice;
                try {
                    choice = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a number.");
                    choice = Integer.parseInt(input.nextLine());    //get user choice
                }

                switch (choice) {
                    case 1:
                        launchSetupClient(); //create or edit quiz - launch setup client
                        break;
                    case 2:
                        launchPlayerClient();  //play quiz - launch player client
                        break;
                    case 3:
                        System.out.println("Thanks for playing!");  //user wants to quit
                        finished = true;
                        return;
                    default:
                        System.out.println("Invalid choice. Please choose an option.");
                        invalid = true; //invalid option so repeat
                        break;
                }
            } while (invalid);
        }
    }

    /**
     * Launch the setup client.
     */
    public void launchSetupClient(){
        SetupClient setup = new SetupClient();
        setup.execute();
    }

    /**
     * Launch the player client.
     */
    public void launchPlayerClient(){
        PlayerClient player = new PlayerClient();
        player.execute();
    }

    /**
     * Print a header surrounded by asterisks
     */
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
    }
}
