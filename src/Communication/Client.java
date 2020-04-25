package Communication;

import Encryption.JEncryptDES;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    protected Socket socket;

    public Client (int port) throws IOException {
        InetAddress address = InetAddress.getLocalHost();
        socket = new Socket(address, port);
    }

    public void run () throws IOException {
        InputStream input = null;
        BufferedReader brinput = null;
        PrintWriter output = null;

        String id_a, id_b, k_m, k_s;
        String m_in, m_out;
        int flag = 1;
        JEncryptDES en_m, en_s;

        try {
            input = socket.getInputStream();
            brinput = new BufferedReader(new InputStreamReader(input));
            output = new PrintWriter(socket.getOutputStream());
        } catch (IOException i) {
            System.out.println("Failure with Establishing BufferedReaders");
        }
        while(true)
            try {
                if (flag == 1) {
                    id_a = "INITIATOR A";
                    k_m = "NETWORK SECURITY";

                    // Print Identity A
                    m_out = id_a;
                    System.out.println(m_out);
                    output.println(m_out);
                    output.flush();

                    // Receive Encrypted Message from Responder B and Decrypt using K_m
                    m_in = brinput.readLine();
                    System.out.println(m_in);
                    en_m = new JEncryptDES(k_m);
                    m_out = en_m.decrypt(m_in);
                    System.out.println(m_out);


                    // Send Identity B Encrypted with K_s
                    String[] message = m_out.split("-", 3);
                    // Space Appended to End of K_s as Required Size of DES Keys is 8 bytes
                    k_s = message[0] + " ";
                    id_b = message[2];
                    en_s = new JEncryptDES(k_s);
                    m_out = en_s.encrypt(id_b);
                    output.println(m_out);
                    output.flush();

                    flag = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}

