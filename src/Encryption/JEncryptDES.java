package Encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.util.Scanner;
import java.util.Base64;

public class JEncryptDES {
    private Cipher encrypt_cipher;
    private Cipher decrypt_cipher;

    public JEncryptDES() throws Exception {
        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
        encrypt_cipher = Cipher.getInstance("DES");
        decrypt_cipher = Cipher.getInstance("DES");
        encrypt_cipher.init(Cipher.ENCRYPT_MODE, key);
        decrypt_cipher.init(Cipher.DECRYPT_MODE, key);
    }

    public JEncryptDES(String user_key) throws Exception{
        byte[] utf8 = user_key.getBytes();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey key = factory.generateSecret(new DESKeySpec(utf8));
        encrypt_cipher = Cipher.getInstance("DES");
        decrypt_cipher = Cipher.getInstance("DES");
        encrypt_cipher.init(Cipher.ENCRYPT_MODE, key);
        decrypt_cipher.init(Cipher.DECRYPT_MODE, key);
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

        // Get User Input
        System.out.print("Please enter the message, \"No body can see me\": ");
        message = input.nextLine();
        System.out.println("Original Message: " + message);

        // Create En/Decrypter Object
        JEncryptDES des_encrypt = new JEncryptDES();

        // Encrypt Message
        encrypted_message = des_encrypt.encrypt(message);
        System.out.println("Encrypted Message: " + encrypted_message);

        // Decrypt Message
        decrypted_message = des_encrypt.decrypt(encrypted_message);
        System.out.println("Decrypted Message: " + decrypted_message);
    }
}
