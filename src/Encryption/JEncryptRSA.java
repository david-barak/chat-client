package Encryption;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Scanner;
import java.util.Base64;

public class JEncryptRSA {
    private Cipher encrypt_cipher;
    private Cipher decrypt_cipher;
    private KeyPair myPair;

    public JEncryptRSA() throws Exception {
        // Create Secret Key Pair and Encrypter/Decrypter Ciphers
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        myPair = kpg.generateKeyPair();
        encrypt_cipher = Cipher.getInstance("RSA");
        decrypt_cipher = Cipher.getInstance("RSA");
        encrypt_cipher.init(Cipher.ENCRYPT_MODE, myPair.getPublic());
        decrypt_cipher.init(Cipher.DECRYPT_MODE, myPair.getPrivate());
    }

    public String encrypt(String str) throws Exception {
        // Encode String using Base64 to get Bytes
        byte[] utf8 = str.getBytes("UTF8");

        // Finish Encrypting String
        String enc = Base64.getEncoder().encodeToString(encrypt_cipher.doFinal(utf8));

        // Encode bytes to base64 to get a string
        return enc;
    }

    public String decrypt(String str) throws Exception {
        // Decode String using Base64 to get Byte
        byte[] dec = Base64.getDecoder().decode(str);

        // Finish Decryption of Bytes and Convert to String
        String decrypted_message = new String(decrypt_cipher.doFinal(dec), "UTF8");

        return decrypted_message;
    }

    public static void main(String[] args) throws Exception {
        // User Input Variables
        Scanner input = new Scanner(System.in);
        String message, encrypted_message, decrypted_message;

        // RSA En/Decryption Object
        JEncryptRSA rsa_encrypt = new JEncryptRSA();

        // Get User Input
        System.out.print("Please enter the message, \"No body can see me\": ");
        message = input.nextLine();
        System.out.println("Original Message: " + message);

        // Encrypt Message
        encrypted_message = rsa_encrypt.encrypt(message);
        System.out.println("Encrypted Message: " + encrypted_message);

        // Decrypt Message
        decrypted_message = rsa_encrypt.decrypt(encrypted_message);
        System.out.println("Decrypted Message: " + decrypted_message);
    }
}

