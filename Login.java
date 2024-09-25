import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Base64;
import java.util.Scanner;

public class Login extends JFrame {
    private File passFile = new File("src/resources/Files/eo3cgEUjJjNT.txt");
    private File keyFile = new File("src/resources/Files/Akq3WktMOiNg.txt");
    private SecretKey rc4Key;
    private String password;
    private JPasswordField textField = new JPasswordField(16);
    private JLabel dummyField = new JLabel();
    private JFrame loginWindow = new JFrame();
    private JPanel formPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();

    public static SecretKey convertStringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, "RC4");
    }

    private void getKey() {
        try {
            Scanner scanner = new Scanner(keyFile);
            String temp = scanner.nextLine();
            scanner.close();
            rc4Key = convertStringToSecretKey(temp);
        } catch (Exception e) {
            System.out.println("Exception in key retrieval.");
        }
    }

    private void loginCheck() {
        String userInput = textField.getText();
        getKey();
        String truePassword = null;
        try {
            Cipher rc4Cipher;
            rc4Cipher = Cipher.getInstance("RC4");
            byte[] text = password.getBytes("UTF8");
            rc4Cipher.init(Cipher.DECRYPT_MODE, rc4Key);
            byte[] textDecrypted = rc4Cipher.doFinal(text);
            truePassword = new String(textDecrypted);
        } catch (Exception exception) {
            System.out.println("Exception in password setup.");
            exception.printStackTrace();
        }
        if (userInput.isEmpty()) {
            dummyField.setText("Please enter a password into the field to login.");
        } else if (userInput.equals(truePassword)) {
            loginWindow.dispose();
            HomeView homeScreen = new HomeView();
        } else {
            dummyField.setText("Password is incorrect.");
        }
    }

    private void buttonPress() {
        loginCheck();
    }

    private class keyPress implements KeyListener {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                loginCheck();
            }
        }

        public void keyPressed(KeyEvent e) {

        }

        public void keyReleased(KeyEvent e) {

        }
    }

    public Login() {
        try {
            Scanner scanner = new Scanner(passFile);
            password = scanner.nextLine();
            scanner.close();
        } catch (Exception exception) {
            System.out.println("Exception in password check.");
        }
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        gbc.insets = new Insets(10, 0, 0, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("Welcome back, _____, to the FTT Program. Enter password for verification."), gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        formPanel.add(textField, gbc);
        gbc.gridx++;
        JButton loginButton = new JButton("Login →");
        loginButton.addActionListener(e -> buttonPress());
        textField.addKeyListener(new keyPress());
        formPanel.add(loginButton, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(dummyField, gbc);
        formPanel.setBackground(Color.decode("#C5D0D0"));
        panel.add(formPanel, BorderLayout.CENTER);
        loginWindow.setContentPane(panel);
        loginWindow.setBounds(100, 100, 750, 450);
        loginWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        loginWindow.setVisible(true);
        loginWindow.getRootPane().setDefaultButton(loginButton);
    }
}