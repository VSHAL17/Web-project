import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SchedulerApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel, roundRobinPanel, srtPanel, sjfPanel, welcomePanel;

    // Round Robin Components
    private JTextField arrivalFieldRR, burstFieldRR, quantumFieldRR;
    private JTable resultTableRR;
    private JTextArea ganttChartAreaRR;

    // SRT Components
    private JTextField arrivalFieldSRT, burstFieldSRT;
    private JTable resultTableSRT;
    private JTextArea ganttChartAreaSRT;

    // SJF Components
    private JTextField arrivalFieldSJF, burstFieldSJF;
    private JTable resultTableSJF;
    private JTextArea ganttChartAreaSJF;

    private JPanel priorityPanel;
    private JTextField arrivalFieldPriority, burstFieldPriority, priorityFieldPriority;
    private JTable resultTablePriority;
    private JTextArea ganttChartAreaPriority;

    public SchedulerApp() {
        setTitle("CPU Scheduling Algorithms");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createWelcomePanel();
        createRoundRobinPanel();
        createSRTPanel();
        createSJFPanel();
        createPriorityPanel();

        mainPanel.add(welcomePanel, "WELCOME");
        mainPanel.add(roundRobinPanel, "ROUND_ROBIN");
        mainPanel.add(srtPanel, "SRT");
        mainPanel.add(sjfPanel, "SJF");
        mainPanel.add(priorityPanel, "PRIORITY");

        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "WELCOME");
    }
    private void createWelcomePanel() {
        // Use a BorderLayout for the main welcome panel
        welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(Color.BLACK);
    
        // Add the title "Choose a scheduling calculator" at the top
        JLabel titleLabel = new JLabel("Choose a Scheduling Calculator", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 80, 0)); // Add spacing
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        welcomePanel.add(titleLabel, BorderLayout.NORTH);
    
        // Create a 2x2 grid for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 20)); // 2 rows, 2 columns, with spacing
        buttonPanel.setBackground(Color.BLACK);
    
        // Create the buttons
        JButton roundRobinButton = new JButton("Round Robin");
        JButton srtButton = new JButton("Shortest Remaining Time");
        JButton sjfButton = new JButton("Shortest Job First");
        JButton priorityButton = new JButton("Non-Preemptive Priority Scheduling");
    
        // Add buttons to the grid panel
        buttonPanel.add(roundRobinButton);      // Top-left
        buttonPanel.add(srtButton);             // Top-right
        buttonPanel.add(sjfButton);             // Bottom-left
        buttonPanel.add(priorityButton);        // Bottom-right
    
        // Add action listeners for the buttons
        roundRobinButton.addActionListener(e -> cardLayout.show(mainPanel, "ROUND_ROBIN"));
        srtButton.addActionListener(e -> cardLayout.show(mainPanel, "SRT"));
        sjfButton.addActionListener(e -> cardLayout.show(mainPanel, "SJF"));
        priorityButton.addActionListener(e -> cardLayout.show(mainPanel, "PRIORITY"));
    
        // Add the button grid to the center of the welcome panel
        welcomePanel.add(buttonPanel, BorderLayout.CENTER);
    
        // Add the welcome panel to the main panel
        mainPanel.add(welcomePanel, "WELCOME");
    }
    private void createRoundRobinPanel() {
        roundRobinPanel = new JPanel(new BorderLayout());
        roundRobinPanel.setBackground(Color.BLACK);
    
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5)); // Adjusted grid layout
        inputPanel.setBorder(BorderFactory.createTitledBorder("Round Robin Input"));
        inputPanel.setBackground(Color.BLACK);
    
        arrivalFieldRR = new JTextField(10);
        burstFieldRR = new JTextField(10);
        quantumFieldRR = new JTextField(10);
        JButton solveButtonRR = new JButton("Solve");
        JButton backButtonRR = new JButton("Back");
    
        inputPanel.add(createStyledLabel("Arrival Times (space-separated):"));
        inputPanel.add(arrivalFieldRR);
        inputPanel.add(createStyledLabel("Burst Times (space-separated):"));
        inputPanel.add(burstFieldRR);
        inputPanel.add(createStyledLabel("Time Quantum:"));
        inputPanel.add(quantumFieldRR);
    
        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5)); // Single row for buttons
        buttonPanel.add(solveButtonRR);
        buttonPanel.add(backButtonRR);
        inputPanel.add(new JLabel()); // Empty label for spacing
        inputPanel.add(buttonPanel);
    
        roundRobinPanel.add(inputPanel, BorderLayout.NORTH);
    
        resultTableRR = createTable();
        ganttChartAreaRR = createGanttChartArea();
    
        roundRobinPanel.add(createOutputPanel(resultTableRR, ganttChartAreaRR), BorderLayout.CENTER);
    
        solveButtonRR.addActionListener(e -> solveRoundRobin());
        backButtonRR.addActionListener(e -> cardLayout.show(mainPanel, "WELCOME"));
    }

    private void createSRTPanel() {
        srtPanel = new JPanel(new BorderLayout());
        srtPanel.setBackground(Color.BLACK);
    
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // Adjusted grid layout
        inputPanel.setBorder(BorderFactory.createTitledBorder("SRT Input"));
        inputPanel.setBackground(Color.BLACK);
    
        arrivalFieldSRT = new JTextField(10);
        burstFieldSRT = new JTextField(10);
        JButton solveButtonSRT = new JButton("Solve");
        JButton backButtonSRT = new JButton("Back");
    
        inputPanel.add(createStyledLabel("Arrival Times (space-separated):"));
        inputPanel.add(arrivalFieldSRT);
        inputPanel.add(createStyledLabel("Burst Times (space-separated):"));
        inputPanel.add(burstFieldSRT);
    
        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5)); // Single row for buttons
        buttonPanel.add(solveButtonSRT);
        buttonPanel.add(backButtonSRT);
        inputPanel.add(new JLabel()); // Empty label for spacing
        inputPanel.add(buttonPanel);
    
        srtPanel.add(inputPanel, BorderLayout.NORTH);
    
        resultTableSRT = createTable();
        ganttChartAreaSRT = createGanttChartArea();
    
        srtPanel.add(createOutputPanel(resultTableSRT, ganttChartAreaSRT), BorderLayout.CENTER);
    
        solveButtonSRT.addActionListener(e -> solveSRT());
        backButtonSRT.addActionListener(e -> cardLayout.show(mainPanel, "WELCOME"));
    }

    private void createSJFPanel() {
        sjfPanel = new JPanel(new BorderLayout());
        sjfPanel.setBackground(Color.BLACK);
    
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // Adjusted grid layout
        inputPanel.setBorder(BorderFactory.createTitledBorder("SJF Input"));
        inputPanel.setBackground(Color.BLACK);
    
        arrivalFieldSJF = new JTextField(10);
        burstFieldSJF = new JTextField(10);
        JButton solveButtonSJF = new JButton("Solve");
        JButton backButtonSJF = new JButton("Back");
    
        inputPanel.add(createStyledLabel("Arrival Times (space-separated):"));
        inputPanel.add(arrivalFieldSJF);
        inputPanel.add(createStyledLabel("Burst Times (space-separated):"));
        inputPanel.add(burstFieldSJF);
    
        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5)); // Single row for buttons
        buttonPanel.add(solveButtonSJF);
        buttonPanel.add(backButtonSJF);
        inputPanel.add(new JLabel()); // Empty label for spacing
        inputPanel.add(buttonPanel);
    
        sjfPanel.add(inputPanel, BorderLayout.NORTH);
    
        resultTableSJF = createTable();
        ganttChartAreaSJF = createGanttChartArea();
    
        sjfPanel.add(createOutputPanel(resultTableSJF, ganttChartAreaSJF), BorderLayout.CENTER);
    
        solveButtonSJF.addActionListener(e -> solveSJF());
        backButtonSJF.addActionListener(e -> cardLayout.show(mainPanel, "WELCOME"));
    }

    private void createPriorityPanel() {
    priorityPanel = new JPanel(new BorderLayout());
    priorityPanel.setBackground(Color.BLACK);

    JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5)); // Adjusted grid layout
    inputPanel.setBorder(BorderFactory.createTitledBorder("Priority Scheduling Input"));
    inputPanel.setBackground(Color.BLACK);

    arrivalFieldPriority = new JTextField(10);
    burstFieldPriority = new JTextField(10);
    priorityFieldPriority = new JTextField(10);
    JButton solveButtonPriority = new JButton("Solve");
    JButton backButtonPriority = new JButton("Back");

    inputPanel.add(createStyledLabel("Arrival Times (space-separated):"));
    inputPanel.add(arrivalFieldPriority);
    inputPanel.add(createStyledLabel("Burst Times (space-separated):"));
    inputPanel.add(burstFieldPriority);
    inputPanel.add(createStyledLabel("Priorities (space-separated, lower number = higher priority):"));
    inputPanel.add(priorityFieldPriority);

    // Create a panel for the buttons
    JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5)); // Single row for buttons
    buttonPanel.add(solveButtonPriority);
    buttonPanel.add(backButtonPriority);
    inputPanel.add(new JLabel()); // Empty label for spacing
    inputPanel.add(buttonPanel);

    priorityPanel.add(inputPanel, BorderLayout.NORTH);

    resultTablePriority = createTable();
    ganttChartAreaPriority = createGanttChartArea();

    priorityPanel.add(createOutputPanel(resultTablePriority, ganttChartAreaPriority), BorderLayout.CENTER);

    solveButtonPriority.addActionListener(e -> solvePriorityScheduling());
    backButtonPriority.addActionListener(e -> cardLayout.show(mainPanel, "WELCOME"));
}

    private void solveSJF() {
        try {
            String[] arrivalInput = arrivalFieldSJF.getText().trim().split("\\s+");
            String[] burstInput = burstFieldSJF.getText().trim().split("\\s+");

            int n = arrivalInput.length;
            int[] arrivalTimes = new int[n];
            int[] burstTimes = new int[n];
            for (int i = 0; i < n; i++) {
                arrivalTimes[i] = Integer.parseInt(arrivalInput[i]);
                burstTimes[i] = Integer.parseInt(burstInput[i]);
            }
            int minArrivalTime = Arrays.stream(arrivalTimes).min().orElse(0);
            int[] finishTimes = new int[n];
            String ganttChart = shortestJobFirst(arrivalTimes, burstTimes, finishTimes, minArrivalTime);
            updateTableAndGantt(resultTableSJF, ganttChartAreaSJF, arrivalTimes, burstTimes, finishTimes, ganttChart);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input! Please check your entries.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void solvePriorityScheduling() {
        try {
            String[] arrivalInput = arrivalFieldPriority.getText().trim().split("\\s+");
            String[] burstInput = burstFieldPriority.getText().trim().split("\\s+");
            String[] priorityInput = priorityFieldPriority.getText().trim().split("\\s+");

            int n = arrivalInput.length;
            int[] arrivalTimes = new int[n];
            int[] burstTimes = new int[n];
            int[] priorities = new int[n];

            for (int i = 0; i < n; i++) {
                arrivalTimes[i] = Integer.parseInt(arrivalInput[i]);
                burstTimes[i] = Integer.parseInt(burstInput[i]);
                priorities[i] = Integer.parseInt(priorityInput[i]);
            }

            int minArrivalTime = Arrays.stream(arrivalTimes).min().orElse(0);

            int[] finishTimes = new int[n];
            String ganttChart = priorityScheduling(arrivalTimes, burstTimes, priorities, finishTimes, minArrivalTime);
            updateTableAndGantt(resultTablePriority, ganttChartAreaPriority, arrivalTimes, burstTimes, finishTimes, ganttChart);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input! Please check your entries.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String priorityScheduling(int[] arrivalTimes, int[] burstTimes, int[] priorities, int[] finishTimes, int minArrivalTime) {
        int n = arrivalTimes.length;
        boolean[] completed = new boolean[n];
        ArrayList<String> processSequence = new ArrayList<>();
        ArrayList<Integer> timeStamps = new ArrayList<>();
        int currentTime = minArrivalTime, completedCount = 0;

        timeStamps.add(minArrivalTime);

        while (completedCount < n) {
            int highestPriority = Integer.MAX_VALUE, idx = -1;

            for (int i = 0; i < n; i++) {
                if (!completed[i] && arrivalTimes[i] <= currentTime && priorities[i] < highestPriority) {
                    highestPriority = priorities[i];
                    idx = i;
                }
            }

            if (idx == -1) {
                int nextArrivalTime = Integer.MAX_VALUE;
                for (int i = 0; i < n; i++) {
                    if (!completed[i] && arrivalTimes[i] < nextArrivalTime) {
                        nextArrivalTime = arrivalTimes[i];
                    }
                }
                if (nextArrivalTime == Integer.MAX_VALUE) {
                    currentTime++;
                } else {
                    currentTime = nextArrivalTime;
                }
            } else {
                processSequence.add("P" + (idx + 1));
                currentTime += burstTimes[idx];
                finishTimes[idx] = currentTime;
                completed[idx] = true;
                completedCount++;
                timeStamps.add(currentTime);
            }
        }
        return formatSummarizedGanttChart(processSequence, timeStamps);
    }


    private JTable createTable() {
        String[] columnNames = {
            "Process", 
            "Arrival Time", 
            "Burst Time", 
            "Finish Time", 
            "Turnaround Time", 
            "Waiting Time"
        };
    
        // Create a non-editable table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };
    
        JTable table = new JTable(tableModel);
        
        // Disable column reordering
        table.getTableHeader().setReorderingAllowed(false);
    
        customizeTable(table); // Apply your existing styling
        return table;
    }

    private JTextArea createGanttChartArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.BOLD, 14));
        area.setBackground(Color.BLACK);
        area.setForeground(Color.WHITE);
        return area;
    }

    private JPanel createOutputPanel(JTable table, JTextArea ganttChartArea) {
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(table), new JScrollPane(ganttChartArea));
        splitPane.setDividerLocation(300);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(splitPane, BorderLayout.CENTER);
        panel.setBackground(Color.BLACK);
        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        return label;
    }

    private void customizeTable(JTable table) {
        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.GRAY);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.DARK_GRAY);
        tableHeader.setForeground(Color.WHITE);
    }

    private void solveSRT() {
        try {
            // Get input from text fields and parse into arrays
            String[] arrivalInput = arrivalFieldSRT.getText().trim().split("\\s+");
            String[] burstInput = burstFieldSRT.getText().trim().split("\\s+");
    
            int n = arrivalInput.length;
            int[] arrivalTimes = new int[n];
            int[] burstTimes = new int[n];
    
            for (int i = 0; i < n; i++) {
                arrivalTimes[i] = Integer.parseInt(arrivalInput[i]);
                burstTimes[i] = Integer.parseInt(burstInput[i]);
            }
    
            // Determine the earliest arrival time
            int minArrivalTime = Arrays.stream(arrivalTimes).min().orElse(0);
    
            // Initialize array for storing finish times
            int[] finishTimes = new int[n];
    
            // Call the SRT scheduling function
            String ganttChart = shortestRemainingTime(arrivalTimes, burstTimes, finishTimes, minArrivalTime);
    
            // Update the UI with the results
            updateTableAndGantt(resultTableSRT, ganttChartAreaSRT, arrivalTimes, burstTimes, finishTimes, ganttChart);
    
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input! Please check your entries.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private String shortestRemainingTime(int[] arrivalTimes, int[] burstTimes, int[] finishTimes, int minArrivalTime) {
        int n = arrivalTimes.length;
        int[] remainingBurst = burstTimes.clone();
        boolean[] completed = new boolean[n];
        ArrayList<String> processSequence = new ArrayList<>();
        ArrayList<Integer> timeStamps = new ArrayList<>();
        int currentTime = minArrivalTime;
        int completedCount = 0;
    
        timeStamps.add(currentTime);
    
        while (completedCount < n) {
            int shortestJob = Integer.MAX_VALUE;
            int idx = -1;
    
            // Find the process with the shortest remaining burst time (prioritize earlier arrival on ties)
            for (int i = 0; i < n; i++) {
                if (!completed[i] && arrivalTimes[i] <= currentTime && remainingBurst[i] > 0) {
                    if (remainingBurst[i] < shortestJob || 
                        (remainingBurst[i] == shortestJob && 
                         (arrivalTimes[i] < arrivalTimes[idx] || 
                          (arrivalTimes[i] == arrivalTimes[idx] && i < idx)))) {
                        shortestJob = remainingBurst[i];
                        idx = i;
                    }
                }
            }
    
            if (idx == -1) {
                currentTime++;
                timeStamps.add(currentTime);
            } else {
                // Add process to Gantt chart if it's a new process
                if (processSequence.isEmpty() || !processSequence.get(processSequence.size() - 1).equals("P" + (idx + 1))) {
                    processSequence.add("P" + (idx + 1));
                    timeStamps.add(currentTime);
                }
    
                remainingBurst[idx]--;
                currentTime++;
    
                // Check if the process completed
                if (remainingBurst[idx] == 0) {
                    finishTimes[idx] = currentTime;
                    completed[idx] = true;
                    completedCount++;
                    timeStamps.add(currentTime);
                }
            }
        }
    
        return formatSRTGanttChart(processSequence, timeStamps);
    }

    private void solveRoundRobin() {
        try {
            String[] arrivalInput = arrivalFieldRR.getText().trim().split("\\s+");
            String[] burstInput = burstFieldRR.getText().trim().split("\\s+");
            int quantum = Integer.parseInt(quantumFieldRR.getText().trim());

            int n = arrivalInput.length;
            int[] arrivalTimes = new int[n];
            int[] burstTimes = new int[n];
            for (int i = 0; i < n; i++) {
                arrivalTimes[i] = Integer.parseInt(arrivalInput[i]);
                burstTimes[i] = Integer.parseInt(burstInput[i]);
            }
            int minArrivalTime = Arrays.stream(arrivalTimes).min().orElse(0);
            int[] finishTimes = new int[n];
            String ganttChart = roundRobin(arrivalTimes, burstTimes, quantum, finishTimes, minArrivalTime);
            updateTableAndGantt(resultTableRR, ganttChartAreaRR, arrivalTimes, burstTimes, finishTimes, ganttChart);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid Input! Please check your entries.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class Process {
        int arrivalTime;
        int burstTime;
        int originalIndex;
    
        public Process(int arrivalTime, int burstTime, int originalIndex) {
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.originalIndex = originalIndex;
        }
    }

    private String roundRobin(int[] arrivalTimes, int[] burstTimes, int quantum, int[] finishTimes, int minArrivalTime) {
        int n = arrivalTimes.length;
        int[] remainingBurst = burstTimes.clone();
        ArrayList<String> processSequence = new ArrayList<>();
        ArrayList<Integer> timeStamps = new ArrayList<>();
        int currentTime = minArrivalTime;
        Queue<Integer> readyQueue = new LinkedList<>();
    
        // Add the initial timestamp only once
        timeStamps.add(currentTime);
    
        List<Process> processes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            processes.add(new Process(arrivalTimes[i], burstTimes[i], i));
        }
    
        processes.sort((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));
    
        boolean[] arrived = new boolean[n];
    
        // Add processes that arrive at or before the current time
        for (Process process : processes) {
            if (process.arrivalTime <= currentTime) {
                readyQueue.add(process.originalIndex);
                arrived[process.originalIndex] = true;
            }
        }
    
        while (!readyQueue.isEmpty()) {
            int currentProcessIndex = readyQueue.poll();
    
            if (remainingBurst[currentProcessIndex] <= 0) continue;
    
            // Add the current time to timeStamps only when a process starts executing
            if (timeStamps.isEmpty() || timeStamps.get(timeStamps.size() - 1) != currentTime) {
                timeStamps.add(currentTime);
            }
            processSequence.add("P" + (currentProcessIndex + 1));
    
            int timeToExecute = Math.min(quantum, remainingBurst[currentProcessIndex]);
            currentTime += timeToExecute;
            remainingBurst[currentProcessIndex] -= timeToExecute;
    
            // Add newly arrived processes to the ready queue
            for (Process process : processes) {
                int i = process.originalIndex;
                if (process.arrivalTime <= currentTime && remainingBurst[i] > 0 && !readyQueue.contains(i) && !arrived[i]) {
                    readyQueue.add(i);
                    arrived[i] = true;
                }
            }
    
            // If the process is not finished, add it back to the ready queue
            if (remainingBurst[currentProcessIndex] > 0) {
                readyQueue.add(currentProcessIndex);
            } else {
                finishTimes[currentProcessIndex] = currentTime;
            }
        }
    
        // Add the final timestamp only once
        if (timeStamps.isEmpty() || timeStamps.get(timeStamps.size() - 1) != currentTime) {
            timeStamps.add(currentTime);
        }
    
        return formatSummarizedGanttChart(processSequence, timeStamps);
    }

    private String shortestJobFirst(int[] arrivalTimes, int[] burstTimes, int[] finishTimes, int minArrivalTime) {
        int n = arrivalTimes.length;
        boolean[] completed = new boolean[n];
        ArrayList<String> processSequence = new ArrayList<>();
        ArrayList<Integer> timeStamps = new ArrayList<>();

        int currentTime = minArrivalTime, completedCount = 0;
        timeStamps.add(minArrivalTime);

        while (completedCount < n) {
            int shortest = Integer.MAX_VALUE, idx = -1;

            for (int i = 0; i < n; i++) {
                if (!completed[i] && arrivalTimes[i] <= currentTime && burstTimes[i] < shortest) {
                    shortest = burstTimes[i];
                    idx = i;
                }
            }

            if (idx == -1) {
                int nextArrivalTime = Integer.MAX_VALUE;
                for (int i = 0; i < n; i++) {
                    if (!completed[i] && arrivalTimes[i] < nextArrivalTime) {
                        nextArrivalTime = arrivalTimes[i];
                    }
                }
                if (nextArrivalTime == Integer.MAX_VALUE) {
                    currentTime++;
                } else {
                    currentTime = nextArrivalTime;
                }
            } else {
                processSequence.add("P" + (idx + 1));
                currentTime += burstTimes[idx];
                finishTimes[idx] = currentTime;
                completed[idx] = true;
                completedCount++;
                timeStamps.add(currentTime);
            }
        }
        return formatSRTGanttChart(processSequence, timeStamps);
    }

    private String formatSummarizedGanttChart(ArrayList<String> processSequence, ArrayList<Integer> timeStamps) {
        StringBuilder chart = new StringBuilder();
        StringBuilder timeline = new StringBuilder();

        // Build the process sequence line
        for (String process : processSequence) {
            chart.append("| ").append(process).append(" ");
        }
        chart.append("|\n");

        // Build the timeline
        for (int i = 0; i < timeStamps.size(); i++) {
            String time = String.valueOf(timeStamps.get(i));
           timeline.append(time);

            // Add spacing to align with the process sequence
            if (i < processSequence.size()) {
                int processWidth = processSequence.get(i).length() + 3; // +3 for "| P" and " " around the process
                int gap = processWidth - time.length();
               for (int j = 0; j < gap; j++) {
                    timeline.append(" ");
               }
            }
        }

        return chart.toString() + timeline.toString();
    }

    private String formatSRTGanttChart(ArrayList<String> processSequence, ArrayList<Integer> timeStamps) {
        StringBuilder chart = new StringBuilder();
        StringBuilder timeline = new StringBuilder();
    
        // Process sequence line
        for (String process : processSequence) {
            chart.append("| ").append(process).append(" ");
        }
        chart.append("|\n");
    
        // Remove consecutive duplicate timestamps
        ArrayList<Integer> filteredTimeStamps = new ArrayList<>();
        Integer prev = null;
        for (Integer ts : timeStamps) {
            if (prev == null || ts != prev) {
                filteredTimeStamps.add(ts);
                prev = ts;
            }
        }
    
        // Build timeline with filtered timestamps
        for (int i = 0; i < filteredTimeStamps.size(); i++) {
            String time = String.valueOf(filteredTimeStamps.get(i));
            timeline.append(time);
    
            // Align with process sequence
            if (i < processSequence.size()) {
                int processWidth = processSequence.get(i).length() + 3;
                int gap = processWidth - time.length();
                for (int j = 0; j < gap; j++) {
                    timeline.append(" ");
                }
            }
        }
    
        return chart.toString() + timeline.toString();
    }

    private void updateTableAndGantt(JTable table, JTextArea area, int[] arrival, int[] burst, int[] finish, String gantt) {
        try {
            int n = arrival.length;
            if (burst.length != n || finish.length != n) {
                throw new IllegalArgumentException("Array lengths do not match!");
            }
    
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing data
    
            int[] turnaroundTimes = new int[n];
            int[] waitingTimes = new int[n];
            double avgTurnaround = 0, avgWaiting = 0;
    
            // Calculate turnaround and waiting times
            for (int i = 0; i < n; i++) {
                turnaroundTimes[i] = finish[i] - arrival[i];
                waitingTimes[i] = turnaroundTimes[i] - burst[i];
                avgTurnaround += turnaroundTimes[i];
                avgWaiting += waitingTimes[i];
            }
    
            avgTurnaround /= n;
            avgWaiting /= n;
    
            // Add process rows
            for (int i = 0; i < n; i++) {
                model.addRow(new Object[]{
                    "P" + (i + 1),
                    arrival[i],
                    burst[i],
                    finish[i],
                    turnaroundTimes[i],
                    waitingTimes[i]
                });
            }
    
            // Add AVERAGE row at the end
            model.addRow(new Object[]{
                "Average",
                "", // Empty for Arrival Time
                "", // Empty for Burst Time
                "", // Empty for Finish Time
                String.format("%.2f", avgTurnaround),
                String.format("%.2f", avgWaiting)
            });
    
            // Update Gantt chart
            if (gantt != null && !gantt.isEmpty()) {
                area.setText(gantt);
            } else {
                area.setText("No Gantt chart available.");
            }
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error updating table or Gantt chart: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SchedulerApp app = new SchedulerApp();
            app.setVisible(true);
    });
}
}