package cpu.scheduling.simulator;

import javax.swing.*;
import java.awt.*;

public class ResultsDisplay extends JFrame {
    public ResultsDisplay(String results) {
        super("Simulation Results");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea resultsArea = new JTextArea(results);
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultsArea);

        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }
}