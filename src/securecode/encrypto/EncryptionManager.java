/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcodeguard.encrypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/*
 * clearTextPath is the path of original sourcefile stored at temp location
 * cipherTextPath is the path where the user wants to save his/her file. at this path encrypted data will be saved
 *
 */
public class EncryptionManager {

    private SecretKey key = null;

    public EncryptionManager() {
        // Genrating Key if it is not generated earliar
        try {

            String filename = "secret.ser";
            int flag = 0;
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = new FileInputStream(filename);
                in = new ObjectInputStream(fis);
                key = (SecretKey) in.readObject();
                in.close();
            } catch (IOException ex) {
                // Here key is being generated
                flag = 1;
                key = KeyGenerator.getInstance("DES").generateKey();


            } catch (ClassNotFoundException ex) {

                ex.printStackTrace();
            }

            // Saving key to use it for decryption
            if (flag == 1) {
                FileOutputStream fos = null;
                ObjectOutputStream out = null;
                try {
                    fos = new FileOutputStream(filename);
                    out = new ObjectOutputStream(fos);
                    out.writeObject(key);
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
        }
    }

    public void encryptSource(String clearTextPath, String cipherTextPath) {

        try {


            // Create encrypter/decrypter class
            EncrypterDecrypter encrypter = new EncrypterDecrypter(key);

            // Encrypt
            encrypter.encrypt(new FileInputStream(clearTextPath), new FileOutputStream(cipherTextPath));
            //encrypter.encrypt(new FileInputStream("d:\\ResultSetDemo.java"), new FileOutputStream("d:\\ResultSetDemo1.java"));

        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
       
       
    }

    public void decryptSource(String clearTextPath, String cipherTextPath) {
        try {
            EncrypterDecrypter encrypter = new EncrypterDecrypter(key);
            encrypter.decrypt(new FileInputStream(cipherTextPath), new FileOutputStream(clearTextPath));
            //encrypter.decrypt(new FileInputStream("d:\\ResultSetDemo1.java"), new FileOutputStream("ResultSetDemo.java"));
        } catch (Exception e) {
        }
    }
}
