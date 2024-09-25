import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Base64;
import java.util.Scanner;

public class PasswordSetup extends JFrame {
    private File keyFile = new File("src/resources/Files/Akq3WktMOiNg.txt");
    private File passFile = new File("src/resources/Files/eo3cgEUjJjNT.txt");
    private SecretKey rc4Key;
    private JPasswordField textField1 = new JPasswordField(16);
    private JPasswordField textField2 = new JPasswordField(16);
    private JLabel dummyField = new JLabel();
    private JFrame setupWindow = new JFrame("Financial Transaction Tracker Program");
    private JPanel formPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();
    private boolean exists = false;
    public static SecretKey convertStringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, "RC4");
    }
    private void getKey() {
        try {
            Scanner scanner = new Scanner(keyFile);
            String temp = scanner.nextLine();
            rc4Key = convertStringToSecretKey(temp);
        } catch (Exception e) {
            System.out.println("Exception in key retrieval.");
        }
    }
    private void LoginButton() {
        setupWindow.dispose();
        Login loginScreen = new Login();
    }

    private void setupCheck() {
        String userInput1 = textField1.getText();
        String userInput2 = textField2.getText();

        if (userInput1.isEmpty() || userInput2.isEmpty()) {
            dummyField.setText("Please enter a password in both fields.");
        } else if (userInput1.equals(userInput2)) {
            try {
                getKey();
                FileWriter writeTo = new FileWriter(passFile, true);
                Cipher rc4Cipher;
                rc4Cipher = Cipher.getInstance("RC4");
                byte[] text = userInput1.getBytes("UTF8");
                rc4Cipher.init(Cipher.ENCRYPT_MODE,rc4Key);
                byte[] textEncrypted = rc4Cipher.doFinal(text);
                String s = new String(textEncrypted);
                writeTo.write(s);
                writeTo.close();
                exists = true;
                dummyField.setText("Password set successfully.");
                JButton loginButton = new JButton("Go to Login →");
                loginButton.addActionListener(new buttonSetup());
                loginButton.addKeyListener(new keyPress());
                setupWindow.getRootPane().setDefaultButton(loginButton);
                gbc.insets = new Insets(20, 0, 0, 0);
                formPanel.add(loginButton, gbc);
            } catch (Exception exception) {
                System.out.println("Exception in password setup.");
            }
        } else {
            dummyField.setText("Please ensure that both password inputs match.");
        }
    }

    private class buttonSetup implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!exists) {
                setupCheck();
            } else {
                LoginButton();
            }

        }
    }

    private class keyPress implements KeyListener {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!exists) {
                    setupCheck();
                } else {
                    LoginButton();
                }
            }
        }

        public void keyPressed(KeyEvent e) {

        }

        public void keyReleased(KeyEvent e) {

        }
    }


    public void passwordSetup() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        gbc.insets = new Insets(5, 0, 0, 5);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(new JLabel("Please set a password for future use of the program. Do not use spaces."), gbc);
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password is case sensitive. Re-input your chosen password in the second box."), gbc);
        textField1.setPreferredSize(new Dimension(30, 30));
        textField2.setPreferredSize(new Dimension(30, 30));
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        formPanel.add(textField1, gbc);
        gbc.gridx = 2;
        gbc.gridheight = 2;
        gbc.ipady = 20;
        JButton setButton = new JButton("Set Password");
        setButton.addActionListener(new buttonSetup());
        setButton.addKeyListener(new keyPress());
        formPanel.add(setButton, gbc);
        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.gridheight = 1;
        gbc.ipady = 0;
        formPanel.add(textField2, gbc);
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        formPanel.add(dummyField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        formPanel.setBackground(Color.decode("#C5D0D0"));
        panel.add(formPanel, BorderLayout.CENTER);
        setupWindow.setContentPane(panel);
        setupWindow.setBounds(100, 100, 750, 450);
        setupWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setupWindow.setVisible(true);
        setupWindow.getRootPane().setDefaultButton(setButton);
    }

    public PasswordSetup() {
        passwordSetup();
    }
}
