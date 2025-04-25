package cpu.scheduling.simulator;
import java.util.*;

class PriorityScheduling implements Scheduler {
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

        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
        int currentTime = 0, index = 0;

        while (!queue.isEmpty() || index < processesCopy.size()) {
            while (index < processesCopy.size() && processesCopy.get(index).arrivalTime <= currentTime) {
                queue.add(processesCopy.get(index++));
            }

            if (!queue.isEmpty()) {
                Process p = queue.poll();
                int start = currentTime;

                currentTime += p.burstTime;

                Map<String, Object> event = new HashMap<>();
                event.put("process", "P" + p.id);
                event.put("start", start);
                event.put("end", currentTime);
                timeline.add(event);

                turnaroundTime.put(p.id, currentTime - p.arrivalTime);
                waitingTime.put(p.id, turnaroundTime.get(p.id) - p.burstTime);
                scheduledOrder.add(p);
            } else if (index < processesCopy.size()) {
                currentTime = processesCopy.get(index).arrivalTime;
            } else {
                currentTime++;
            }
        }

        double totalWaiting = 0, totalTurnaround = 0;
        for (Process p : processesCopy) {
            totalWaiting += waitingTime.getOrDefault(p.id, 0);
            totalTurnaround += turnaroundTime.getOrDefault(p.id, 0);
        }
        avgWaitingTime = totalWaiting / processesCopy.size();
        avgTurnaroundTime = totalTurnaround / processesCopy.size();
    }

    @Override
    public Map<String, Object> getResults() {
        Map<String, Object> results = new HashMap<>();
        results.put("algorithm", "Priority Scheduling");
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