import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IndexView extends JFrame {
    private static List<Event> events = new ArrayList<>();
    private File file = new File("src/resources/Files/m87U0nM12v5h.txt");

    public void EventTable(List<Event> events) {
        this.events = events;
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Type", "Frequency", "Amount", "Day", "Month", "Year", "Notes"}, 0);
        for (Event event : events) {
            tableModel.addRow(new Object[]{event.getType(), event.getFreq(), event.getAmountVal(), event.getDD(), event.getMM(), event.getYY(), event.getNotes()});
        }
        JTable jTable = new JTable(tableModel);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        String[] columns = {"Type", "Frequency", "Amount", "Day", "Month", "Year", "Notes"};
        JComboBox<String> columnComboBox = new JComboBox<>(columns);
        String[] orders = {"Ascending", "Descending"};
        JComboBox<String> orderComboBox = new JComboBox<>(orders);
        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> sortTable(jTable, columnComboBox.getSelectedItem().toString(), orderComboBox.getSelectedItem().toString()));
        JButton openChartButton = new JButton("Open Chart");
        openChartButton.addActionListener(e -> openChartFrame());
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Sort by: "));
        controlPanel.add(columnComboBox);
        controlPanel.add(new JLabel("Order: "));
        controlPanel.add(orderComboBox);
        controlPanel.add(sortButton);
        controlPanel.add(new JLabel("Sort by: "));
        controlPanel.add(columnComboBox);
        controlPanel.add(new JLabel("Order: "));
        controlPanel.add(orderComboBox);
        controlPanel.add(sortButton);
        controlPanel.add(openChartButton);
        setTitle("Event Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(jScrollPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void sortTable(JTable table, String columnName, String sortOrder) {
        Comparator<Event> comparator = Comparator.comparing(e -> {
            switch (columnName) {
                case "Type":
                    return e.getType();
                case "Frequency":
                    return e.getFreq();
                case "Amount":
                    return e.getAmount();
                case "Day":
                    return e.getDD();
                case "Month":
                    return e.getMM();
                case "Year":
                    return e.getYY();
                case "Notes":
                    return e.getNotes();
                default:
                    return "";
            }
        });

        if (sortOrder.equals("Descending")) {
            comparator = comparator.reversed();
        }

        List<Event> sortedEvents = new ArrayList<>(events);
        sortedEvents.sort(comparator);

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);

        for (Event event : sortedEvents) {
            tableModel.addRow(new Object[]{event.getType(), event.getFreq(), event.getAmount(), event.getDD(), event.getMM(), event.getYY(), event.getNotes()});
        }
    }

    private void openChartFrame() {
        dispose();
        ScattergramChartFrame chartView = new ScattergramChartFrame(events);
    }

    private void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                String tempType = parts[0];
                String tempFreq = parts[1];
                String tempAmount = parts[2];
                String tempDay = parts[3];
                String tempMonth = parts[4];
                String tempYear = parts[5];
                String tempNotes = parts[6];
                Event event = new Event(tempType, tempFreq, tempAmount, tempDay, tempMonth, tempYear, tempNotes);
                events.add(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IndexView() {
        readFile();
        EventTable(events);
    }
}
