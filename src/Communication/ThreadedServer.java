package Communication;

import java.net.*;
import java.io.*;

public class ThreadedServer {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    public ThreadedServer(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server Started...");
        } catch (IOException i) {
            System.err.println("Error Creating Server!");
        }

        while (true) {
            try {
                // Wait for New Client to Connect
                socket = server.accept();
            } catch (IOException i) {
                System.out.println("Error Accepting Client!");
            }

            new ServerThread(socket).start();
        }
    }
}
