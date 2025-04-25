package cpu.scheduling.simulator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CPUSchedulingSimulatorGUI extends JFrame {
    private final List<Process> processes = new ArrayList<>();
    private DefaultTableModel processTableModel;
    private JTable processTable;
    private JComboBox<String> algorithmSelector;
    private JTextField timeQuantumField;
    private JButton runButton;
    private JButton addProcessButton;
    private JButton removeProcessButton;
    private JButton clearAllButton;
    private JTextField idField;
    private JTextField arrivalTimeField;
    private JTextField burstTimeField;
    private JTextField priorityField;

    public CPUSchedulingSimulatorGUI() {
        super("CPU Scheduling Simulator");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel processPanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout());

        String[] columns = {"Process ID", "Arrival Time", "Burst Time", "Priority"};
        processTableModel = new DefaultTableModel(columns, 0);
        processTable = new JTable(processTableModel);
        JScrollPane tableScrollPane = new JScrollPane(processTable);
        processPanel.add(new JLabel("Process Table:"), BorderLayout.NORTH);
        processPanel.add(tableScrollPane, BorderLayout.CENTER);

        inputPanel.add(new JLabel("Process ID:"));
        idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Arrival Time:"));
        arrivalTimeField = new JTextField();
        inputPanel.add(arrivalTimeField);
        inputPanel.add(new JLabel("Burst Time:"));
        burstTimeField = new JTextField();
        inputPanel.add(burstTimeField);
        inputPanel.add(new JLabel("Priority (lower is higher):"));
        priorityField = new JTextField();
        inputPanel.add(priorityField);
        inputPanel.add(new JLabel("Select Algorithm:"));
        algorithmSelector = new JComboBox<>(new String[]{
                "FCFS", "Round Robin", "SPN", "SRTN", "Priority", "All"
        });
        inputPanel.add(algorithmSelector);
        inputPanel.add(new JLabel("Time Quantum (for RR):"));
        timeQuantumField = new JTextField("2");
        inputPanel.add(timeQuantumField);
        inputPanel.setBackground(new Color(0, 255, 204));

        addProcessButton = new JButton("Add Process");
        removeProcessButton = new JButton("Remove Selected");
        clearAllButton = new JButton("Clear All");
        runButton = new JButton("Run Simulation");
        buttonPanel.add(addProcessButton);
        buttonPanel.add(removeProcessButton);
        buttonPanel.add(clearAllButton);
        buttonPanel.add(runButton);

        controlPanel.add(inputPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        topPanel.add(processPanel, BorderLayout.CENTER);
        topPanel.add(controlPanel, BorderLayout.EAST);

        JPanel ganttChartPanel = new JPanel(new BorderLayout());
        ganttChartPanel.add(new JLabel("Gantt Chart:"), BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);
        add(ganttChartPanel, BorderLayout.CENTER);

        addProcessButton.addActionListener(e -> addProcess());
        removeProcessButton.addActionListener(e -> removeSelectedProcess());
        clearAllButton.addActionListener(e -> clearAllProcesses());
        runButton.addActionListener(e -> runSimulation(ganttChartPanel));
        addProcessButton.setBackground(new Color(144, 238, 0));
        removeProcessButton.setBackground(new Color(255, 160, 122));
        clearAllButton.setBackground(new Color(255, 0, 185));
        runButton.setBackground(new Color(176, 0, 230));
        idField.setText("1");

        setVisible(true);
    }

    private void addProcess() {
        try {
            int id = Integer.parseInt(idField.getText());
            int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
            int burstTime = Integer.parseInt(burstTimeField.getText());
            int priority = Integer.parseInt(priorityField.getText());
            if (burstTime <= 0) {
                JOptionPane.showMessageDialog(this, "Burst time must be greater than 0", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (Process p : processes) {
                if (p.id == id) {
                    JOptionPane.showMessageDialog(this, "Process ID already exists", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            Process process = new Process(id, arrivalTime, burstTime, priority);
            processes.add(process);
            processTableModel.addRow(new Object[]{id, arrivalTime, burstTime, priority});
            idField.setText(String.valueOf(id + 1));
            arrivalTimeField.setText("");
            burstTimeField.setText("");
            priorityField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeSelectedProcess() {
        int selectedRow = processTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) processTableModel.getValueAt(selectedRow, 0);
            processes.removeIf(p -> p.id == id);
            processTableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a process to remove", "No Selection", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearAllProcesses() {
        processes.clear();
        while (processTableModel.getRowCount() > 0) {
            processTableModel.removeRow(0);
        }
        idField.setText("1");
    }

    private void runSimulation(JPanel ganttChartPanel) {
        if (processes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one process", "No Processes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String selectedAlgorithm = (String) algorithmSelector.getSelectedItem();
        int timeQuantum = 2;
        if (selectedAlgorithm.equals("Round Robin") || selectedAlgorithm.equals("All")) {
            try {
                timeQuantum = Integer.parseInt(timeQuantumField.getText());
                if (timeQuantum <= 0) {
                    JOptionPane.showMessageDialog(this, "Time quantum must be greater than 0", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid time quantum", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        StringBuilder results = new StringBuilder();
        switch (selectedAlgorithm) {
            case "FCFS":
                runAlgorithm(new FCFS(), results, ganttChartPanel);
                break;
            case "Round Robin":
                runAlgorithm(new RoundRobin(timeQuantum), results, ganttChartPanel);
                break;
            case "SPN":
                runAlgorithm(new SPN(), results, ganttChartPanel);
                break;
            case "SRTN":
                runAlgorithm(new SRTN(), results, ganttChartPanel);
                break;
            case "Priority":
                runAlgorithm(new PriorityScheduling(), results, ganttChartPanel);
                break;
            case "All":
                runAlgorithm(new FCFS(), results, ganttChartPanel);
                results.append("\n");
                runAlgorithm(new RoundRobin(timeQuantum), results, ganttChartPanel);
                results.append("\n");
                runAlgorithm(new SPN(), results, ganttChartPanel);
                results.append("\n");
                runAlgorithm(new SRTN(), results, ganttChartPanel);
                results.append("\n");
                runAlgorithm(new PriorityScheduling(), results, ganttChartPanel);
                break;
        }

        new ResultsDisplay(results.toString());
    }

    private void runAlgorithm(Scheduler scheduler, StringBuilder results, JPanel ganttChartPanel) {
        scheduler.schedule(processes);
        Map<String, Object> resultData = scheduler.getResults();
        List<Map<String, Object>> timeline = scheduler.getTimeline();

        results.append(resultData.get("algorithm")).append(" Results:\n");
        results.append("--------------------------------------------------\n");
        List<Process> order = (List<Process>) resultData.get("order");
        results.append("Order of Completion: ");
        for (Process p : order) {
            results.append("P").append(p.id).append(" ");
        }
        results.append("\n");

        Map<Integer, Integer> waitingTime = (Map<Integer, Integer>) resultData.get("waitingTime");
        Map<Integer, Integer> turnaroundTime = (Map<Integer, Integer>) resultData.get("turnaroundTime");
        results.append(String.format("%-10s %-15s %-15s\n", "Process", "Waiting Time", "Turnaround Time"));
        results.append("--------------------------------------------------\n");
        for (Process p : processes) {
            results.append(String.format("%-10s %-15d %-15d\n",
                    "P" + p.id,
                    waitingTime.getOrDefault(p.id, 0),
                    turnaroundTime.getOrDefault(p.id, 0)));
        }
        results.append("--------------------------------------------------\n");
        results.append(String.format("Average Waiting Time: %.2f\n", (double) resultData.get("avgWaitingTime")));
        results.append(String.format("Average Turnaround Time: %.2f\n", (double) resultData.get("avgTurnaroundTime")));

        ganttChartPanel.removeAll();
        ganttChartPanel.add(new JLabel("Gantt Chart:"), BorderLayout.NORTH);
        ganttChartPanel.add(new GanttChartPanel(timeline), BorderLayout.CENTER);
        ganttChartPanel.revalidate();
        ganttChartPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CPUSchedulingSimulatorGUI());
    }
}