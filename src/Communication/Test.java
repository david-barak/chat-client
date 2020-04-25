package Communication;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String userChoice;
        System.out.print("Enter 1 for Server or 2 for Client: ");
        userChoice = input.nextLine();

        if (userChoice.equals("1")) {
            ThreadedServer server = new ThreadedServer(5000);
        } else if (userChoice.equals("2")) {
            Client client_a = new Client(5000);
            client_a.run();
        }
    }
}
