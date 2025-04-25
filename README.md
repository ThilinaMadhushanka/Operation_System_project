<div align="center">
  <h1 style="font-size: 60px; color: yellow;">CPU Scheduling Simulator</h1>
</div>
<p align="center">
  <a href="https://git.io/typing-svg">
    <img src="https://readme-typing-svg.herokuapp.com?font=Fira+Code&size=20&pause=1000&color=683FF7&background=E0FF0000&center=true&vCenter=true&width=800&lines=A+Java+based+graphical+simulator+for+visualizing+and+comparing;different+CPU+scheduling+algorithms+used+in+operating+systems." alt="Typing SVG" />
  </a>
</p>

<img src="https://github.com/Govindv7555/Govindv7555/blob/main/49e76e0596857673c5c80c85b84394c1.gif" width="100%" height="95px">

## Overview

This application provides a graphical user interface to simulate and compare various CPU scheduling algorithms. It allows users to add processes with different arrival times, burst times, and priorities, then visualize how different scheduling algorithms would handle these processes.

<img src="https://github.com/Govindv7555/Govindv7555/blob/main/49e76e0596857673c5c80c85b84394c1.gif" width="100%" height="95px">

## Features

- Intuitive GUI for adding, removing, and managing processes
- Support for multiple CPU scheduling algorithms:
  - First Come First Served (FCFS)
  - Round Robin (RR) with configurable time quantum
  - Shortest Process Next (SPN)
  - Shortest Remaining Time Next (SRTN)
  - Priority Scheduling
- Visual Gantt chart to show process execution timeline
- Detailed performance metrics for each algorithm:
  - Process completion order
  - Waiting time for each process
  - Turnaround time for each process
  - Average waiting time
  - Average turnaround time
- Ability to compare all algorithms at once

<img src="https://github.com/Govindv7555/Govindv7555/blob/main/49e76e0596857673c5c80c85b84394c1.gif" width="100%" height="95px">

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java Swing (included in JDK)

### Running the Application

1. Compile all Java files:
   ```
   javac -d bin cpu/scheduling/simulator/*.java
   ```

2. Run the application:
   ```
   java -cp bin cpu.scheduling.simulator.CPUSchedulingSimulatorGUI
   ```

Alternatively, you can import the project into an IDE like Eclipse or IntelliJ IDEA and run it from there.

<img src="https://github.com/Govindv7555/Govindv7555/blob/main/49e76e0596857673c5c80c85b84394c1.gif" width="100%" height="95px">

## Usage Guide

1. **Adding Processes**:
   - Enter Process ID, Arrival Time, Burst Time, and Priority
   - Click "Add Process"

2. **Managing Processes**:
   - Select a process in the table and click "Remove Selected" to delete it
   - Click "Clear All" to remove all processes

3. **Running Simulations**:
   - Select an algorithm from the dropdown menu
   - For Round Robin, specify a time quantum
   - Click "Run Simulation"
   - View the Gantt chart and results window

4. **Interpreting Results**:
   - The Gantt chart shows which process runs at what time
   - The results window shows detailed metrics for each process
   - Compare the average waiting and turnaround times

<img src="https://github.com/Govindv7555/Govindv7555/blob/main/49e76e0596857673c5c80c85b84394c1.gif" width="100%" height="95px">

## Understanding the Algorithms

### First Come First Served (FCFS)
Processes are executed in the order they arrive. Simple but can lead to the "convoy effect" where short processes wait behind long ones.

### Round Robin (RR)
Each process is given a fixed time slice (quantum). If a process doesn't complete within its time quantum, it's moved to the back of the queue. Provides fair CPU distribution but increases context switching.

### Shortest Process Next (SPN)
Selects the process with the shortest burst time. Minimizes average waiting time but can lead to starvation of longer processes.

### Shortest Remaining Time Next (SRTN)
Preemptive version of SPN. If a new process arrives with a shorter burst time than the remaining time of the current process, the CPU switches to the new process.

### Priority Scheduling
Processes are executed based on priority (lower value means higher priority). Can lead to starvation of low-priority processes.

<img src="https://github.com/Govindv7555/Govindv7555/blob/main/49e76e0596857673c5c80c85b84394c1.gif" width="100%" height="95px">

## Project Structure

- `CPUSchedulingSimulatorGUI.java`: Main application class and GUI
- `Process.java`: Process representation
- `Scheduler.java`: Interface for all scheduling algorithms
- `FCFS.java`: First Come First Served implementation
- `RoundRobin.java`: Round Robin implementation
- `SPN.java`: Shortest Process Next implementation
- `SRTN.java`: Shortest Remaining Time Next implementation
- `PriorityScheduling.java`: Priority Scheduling implementation
- `GanttChartPanel.java`: Visualizes process execution timeline
- `ResultsDisplay.java`: Shows detailed algorithm results

<img src="https://github.com/Govindv7555/Govindv7555/blob/main/49e76e0596857673c5c80c85b84394c1.gif" width="100%" height="95px">

## License

This project is available for educational purposes. Feel free to modify and distribute it according to your needs.

<img src="https://github.com/Govindv7555/Govindv7555/blob/main/49e76e0596857673c5c80c85b84394c1.gif" width="100%" height="95px">

## Acknowledgements

This simulator was designed to help students understand CPU scheduling algorithms in operating systems courses.
---
