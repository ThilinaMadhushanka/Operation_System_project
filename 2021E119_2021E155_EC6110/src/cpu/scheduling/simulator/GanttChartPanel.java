package cpu.scheduling.simulator;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class GanttChartPanel extends JPanel {
    private List<Map<String, Object>> timeline;

    public GanttChartPanel(List<Map<String, Object>> timeline) {
        this.timeline = timeline;
        setPreferredSize(new Dimension(800, 200));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (timeline == null || timeline.isEmpty()) {
            g2d.drawString("No timeline data available", 10, 20);
            return;
        }

        int xStart = 50, yStart = 50, barHeight = 30;
        int maxTime = 0;

        for (Map<String, Object> event : timeline) {
            maxTime = Math.max(maxTime, (int) event.get("end"));
        }

        g2d.setColor(Color.BLACK);
        g2d.drawLine(xStart, yStart + barHeight, xStart + 700, yStart + barHeight);

        int[] colors = {Color.RED.getRGB(), Color.BLUE.getRGB(), Color.GREEN.getRGB(), Color.ORANGE.getRGB()};
        int colorIndex = 0;

        for (Map<String, Object> event : timeline) {
            String process = (String) event.get("process");
            int start = (int) event.get("start");
            int end = (int) event.get("end");

            int x1 = xStart + (start * 700 / maxTime);
            int x2 = xStart + (end * 700 / maxTime);

            g2d.setColor(new Color(colors[colorIndex % colors.length]));
            g2d.fillRect(x1, yStart, x2 - x1, barHeight);

            g2d.setColor(Color.BLACK);
            g2d.drawString(process, x1 + 5, yStart + barHeight / 2 + 5);

            colorIndex++;
        }

        for (int i = 0; i <= maxTime; i++) {
            int xPos = xStart + (i * 700 / maxTime);
            g2d.drawLine(xPos, yStart + barHeight, xPos, yStart + barHeight + 5);
            g2d.drawString(String.valueOf(i), xPos, yStart + barHeight + 20);
        }
    }
}