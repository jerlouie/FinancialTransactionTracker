import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileWriter;
import java.util.Base64;

public class FTT_Program {
    private static File keyFile = new File("src/resources/Files/Akq3WktMOiNg.txt");
    private static File passFile = new File("src/resources/Files/eo3cgEUjJjNT.txt");
    public static String convertSecretKeyToString(SecretKey secretKey) {
        byte[] rawData = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(rawData);
    }

    public static void main(String[] args) {
        try {
            if (!keyFile.exists()) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("RC4");
                SecretKey rc4Key = keyGenerator.generateKey();
                FileWriter writeTo = new FileWriter(keyFile, true);
                writeTo.write(convertSecretKeyToString(rc4Key));
                writeTo.close();
            }
        } catch (Exception e) {
            System.out.println("Exception in encryption generation");
            e.printStackTrace();
        }
        try {
            if (passFile.exists()) {
                Login directLoginScreen = new Login();
            } else {
                PasswordSetup setupScreen = new PasswordSetup();
            }
        } catch (Exception exception) {
            System.out.println("Exception in login directory.");
        }
    }
}