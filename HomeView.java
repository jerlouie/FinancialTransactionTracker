import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Base64;
import java.util.Scanner;

public class HomeView extends JFrame {
    private File passFile = new File("src/resources/Files/m87U0nM12v5h.txt");
    private File keyFile = new File("src/resources/Files/Akq3WktMOiNg.txt");
    private SecretKey rc4Key;
    private JTextField mmInput = new JTextField(2);
    private JTextField ddInput = new JTextField(2);
    private JTextField yyInput = new JTextField(2);
    String[] c1 = {"Buy", "Sell", "Deposit", "Withdrawal"};
    private JComboBox typeInput = new JComboBox(c1);
    String[] c2 = {"Once", "Twice", "Multiple", "Later"};
    private JComboBox freqInput = new JComboBox(c2);
    private JTextField noteInput = new JTextField(20);
    private JTextField amountInput = new JTextField(4);
    private JLabel dummyField = new JLabel();
    private JFrame homeWindow = new JFrame();
    private JPanel addEventPanel = new JPanel(new GridBagLayout());
    private JPanel goToIndexPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints gbc = new GridBagConstraints();
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

    private void addEvent() {
        String typeString = typeInput.getSelectedItem().toString();
        String freqString = freqInput.getSelectedItem().toString();
        String amountString = amountInput.getText();
        String mmString = mmInput.getText();
        String ddString = ddInput.getText();
        String yyString = yyInput.getText();
        String noteString = noteInput.getText();
        if (noteString.isEmpty()) {
            noteString = " ";
        }
        if (typeString.isEmpty() || freqString.isEmpty() || amountString.isEmpty() || mmString.isEmpty() || ddString.isEmpty() || yyString.isEmpty()) {
            dummyField.setText("Please ensure all required fields are filled.");
            dummyField.setForeground(Color.decode("#E45957"));
        } else {
            BufferedWriter buffWrite = null;
            getKey();
            try {
                FileWriter writeTo = new FileWriter(passFile, true);
                String line = typeString + "|" + freqString + "|" + amountString + "|" + mmString + "|" + ddString + "|" + yyString + "|" + noteString;
                String s = new String(line);
                buffWrite = new BufferedWriter(writeTo);
                buffWrite.newLine();
                buffWrite.write(s);
                buffWrite.close();
                mmInput.setText("");
                ddInput.setText("");
                yyInput.setText("");
                noteInput.setText("");
                amountInput.setText("");
                dummyField.setText("New event added successfully.");
            } catch (Exception exception) {
                System.out.println("Exception in event log setup.");
            } finally {
                try {
                    if (buffWrite != null) {
                        buffWrite.close();
                    }
                } catch (Exception e) {
                    System.out.println("Error closing text file.");
                }
            }
        }
    }

    private void enterPress() {
        addEvent();
    }

    private class keyPress implements KeyListener {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                addEvent();
            }
        }

        public void keyPressed(KeyEvent e) {

        }

        public void keyReleased(KeyEvent e) {

        }
    }

    private void goPress() {
        homeWindow.dispose();
        IndexView indexWindow = new IndexView();
    }

    public HomeView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));
        JLabel greeting = new JLabel("Welcome, _____");
        greeting.setFont(new Font("Serif", Font.BOLD, 18));
        greeting.setBorder(new EmptyBorder(0, 0, 30, 0));
        panel.add(greeting, BorderLayout.NORTH);
        JLabel eventTitle = new JLabel("Add new transaction to log:");
        eventTitle.setFont(new Font("Serif", Font.BOLD, 14));
        gbc.insets = new Insets(2, 2, 6, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 10;
        addEventPanel.add(eventTitle, gbc);
        gbc.insets = new Insets(5, 5, 5, 2);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 6;
        gbc.gridy++;
        gbc.gridwidth = 1;
        addEventPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        typeInput.setBorder(new BubbleBorder(Color.BLACK, 1, 1, 0));
        addEventPanel.add(typeInput, gbc);
        gbc.gridx = 9;
        gbc.gridwidth = 1;
        addEventPanel.add(new JLabel("Frequency:"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        freqInput.setBorder(new BubbleBorder(Color.BLACK, 1, 1, 0));
        addEventPanel.add(freqInput, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.2;
        addEventPanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 8;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        addEventPanel.add(noteInput, gbc);
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        addEventPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx++;
        gbc.gridwidth = 1;
        addEventPanel.add(amountInput, gbc);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        addEventPanel.add(new JLabel("mm/dd/yy:"), gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx++;
        addEventPanel.add(mmInput, gbc);
        gbc.gridx++;
        addEventPanel.add(new JLabel("/"), gbc);
        gbc.gridx++;
        addEventPanel.add(ddInput, gbc);
        gbc.gridx++;
        addEventPanel.add(new JLabel("/"), gbc);
        gbc.gridx++;
        addEventPanel.add(yyInput, gbc);
        gbc.gridx = 11;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy++;
        gbc.gridwidth = 1;
        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> enterPress());
        enterButton.addKeyListener(new keyPress());
        addEventPanel.add(enterButton, gbc);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 10;
        addEventPanel.add(dummyField, gbc);
        addEventPanel.setBackground(Color.decode("#CBF7F7"));
        panel.setBackground(Color.decode("#C5D0D0"));
        panel.add(addEventPanel, BorderLayout.WEST);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets.bottom = 10;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel header2 = new JLabel("         Go to data portal:         ");
        header2.setFont(new Font("Serif", Font.BOLD, 16));
        goToIndexPanel.add(header2, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        JButton input = new JButton("  →  ");
        input.addActionListener(e -> goPress());
        input.setBorder(new BubbleBorder(Color.BLACK, 1, 1, 0));
        input.setBackground(Color.WHITE);
        goToIndexPanel.add(input, gbc);
        goToIndexPanel.setBackground(Color.decode("#D9E8D9"));
        panel.add(goToIndexPanel, BorderLayout.EAST);
        homeWindow.getRootPane().setDefaultButton(enterButton);
        homeWindow.setContentPane(panel);
        homeWindow.setBounds(100, 100, 900, 450);
        homeWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
        homeWindow.setVisible(true);
    }
}
