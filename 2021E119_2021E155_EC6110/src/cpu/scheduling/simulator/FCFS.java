package cpu.scheduling.simulator;
import java.util.*;

class FCFS implements Scheduler {
    private List<Process> scheduledOrder = new ArrayList<>();
    private Map<Integer, Integer> waitingTime = new HashMap<>();
    private Map<Integer, Integer> turnaroundTime = new HashMap<>();
    private double avgWaitingTime = 0;
    private double avgTurnaroundTime = 0;
    private List<Map<String, Object>> timeline = new ArrayList<>();

    @Override
    public void schedule(List<Process> processes) {
        List<Process> processesCopy = new ArrayList<>();
        for (Process p : processes) {
            processesCopy.add(p.clone());
        }
        processesCopy.sort(Comparator.comparingInt(p -> p.arrivalTime));

        int currentTime = 0;
        for (Process p : processesCopy) {
            if (currentTime < p.arrivalTime) {
                currentTime = p.arrivalTime;
            }
            waitingTime.put(p.id, currentTime - p.arrivalTime);
            turnaroundTime.put(p.id, waitingTime.get(p.id) + p.burstTime);

            // Add to timeline
            Map<String, Object> event = new HashMap<>();
            event.put("process", "P" + p.id);
            event.put("start", currentTime);
            event.put("end", currentTime + p.burstTime);
            timeline.add(event);

            currentTime += p.burstTime;
            scheduledOrder.add(p);
        }

        // Calculate averages
        double totalWaiting = 0, totalTurnaround = 0;
        for (Process p : processesCopy) {
            totalWaiting += waitingTime.get(p.id);
            totalTurnaround += turnaroundTime.get(p.id);
        }
        avgWaitingTime = totalWaiting / processesCopy.size();
        avgTurnaroundTime = totalTurnaround / processesCopy.size();
    }

    @Override
    public Map<String, Object> getResults() {
        Map<String, Object> results = new HashMap<>();
        results.put("algorithm", "First Come First Served (FCFS)");
        results.put("order", scheduledOrder);
        results.put("waitingTime", waitingTime);
        results.put("turnaroundTime", turnaroundTime);
        results.put("avgWaitingTime", avgWaitingTime);
        results.put("avgTurnaroundTime", avgTurnaroundTime);
        return results;
    }

    @Override
    public List<Map<String, Object>> getTimeline() {
        return timeline;
    }
}