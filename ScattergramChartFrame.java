import org.knowm.xchart.XYChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.SeriesMarkers;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScattergramChartFrame extends JFrame {
    private List<Event> events;

    public ScattergramChartFrame(List<Event> events) {
        this.events = events;
        XChartPanel<XYChart> chartPanel = createScattergramChart();
        add(chartPanel, BorderLayout.CENTER);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private XChartPanel<XYChart> createScattergramChart() {
        List<Integer> dates = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        for (Event event : events) {
            dates.add(event.dateCompile());
            amounts.add(event.getAmountVal());
        }
        XYChart chart = new XYChartBuilder().width(800).height(600).title("Scattergram Plot").xAxisTitle("Date").yAxisTitle("Amount").build();
        chart.addSeries("Scatter", dates, amounts).setMarker(SeriesMarkers.CIRCLE);
        return new XChartPanel<>(chart);
    }
}