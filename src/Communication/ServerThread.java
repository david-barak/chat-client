package Communication;

import java.io.*;
import java.net.Socket;
import Encryption.JEncryptDES;

public class ServerThread extends Thread {
    protected Socket socket;

    public ServerThread(Socket clientSocket) {
        this.socket = clientSocket;
        this.run();
    }

    public void run() {
        InputStream input;
        BufferedReader brinput = null;
        PrintWriter output = null;

        try {
            input = socket.getInputStream();
            brinput = new BufferedReader(new InputStreamReader(input));
            output = new PrintWriter(socket.getOutputStream());
        } catch (IOException i) {
            System.out.println("Failure with Establishing BufferedReaders");
        }
        String id_a, id_b, k_m, k_s;
        String m_in, m_out;
        int flag = 1;
        JEncryptDES en_m, en_s;
        while(true) {
            if (flag == 1) {
                try {
                    id_b = "RESPONDER B";
                    k_m = "NETWORK SECURITY";
                    k_s = "RYERSON ";

                    // Read Identity A from Socket
                    m_in = brinput.readLine();
                    System.out.println(m_in);
                    id_a = m_in;

                    // Responder B Response with Session Key, Identity A, and Identity
                    en_m = new JEncryptDES(k_m);
                    m_out = en_m.encrypt(k_s + "-" + id_a + "-" + id_b);
                    System.out.println(m_out);
                    output.println(m_out);
                    output.flush();


                    // Response B Receives Response from Initiator A and Decrypts using Session Key
                    en_s = new JEncryptDES(k_s);
                    m_in = brinput.readLine();
                    System.out.println(m_in);
                    m_out = en_s.decrypt(m_in);
                    System.out.println(m_out);

                    flag = 0;
                } catch (IOException i) {
                    i.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
