package cpu.scheduling.simulator;

import java.util.*;

class SRTN implements Scheduler {
    private List<Process> scheduledOrder = new ArrayList<>();
    private Map<Integer, Integer> waitingTime = new HashMap<>();
    private Map<Integer, Integer> turnaroundTime = new HashMap<>();
    private double avgWaitingTime = 0;
    private double avgTurnaroundTime = 0;
    private Set<Integer> completedProcesses = new HashSet<>();
    private List<Map<String, Object>> timeline = new ArrayList<>();

    @Override
    public void schedule(List<Process> processes) {
        List<Process> processesCopy = new ArrayList<>();
        for (Process p : processes) {
            processesCopy.add(p.clone());
        }
        processesCopy.sort(Comparator.comparingInt(p -> p.arrivalTime));

        PriorityQueue<Process> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));
        int currentTime = 0, index = 0;
        Process currentProcess = null;

        while (!queue.isEmpty() || index < processesCopy.size() || currentProcess != null) {
            while (index < processesCopy.size() && processesCopy.get(index).arrivalTime <= currentTime) {
                queue.add(processesCopy.get(index++));
            }

            if (currentProcess != null && !queue.isEmpty()) {
                Process nextProcess = queue.peek();
                if (nextProcess.remainingTime < currentProcess.remainingTime) {
                    queue.add(currentProcess);
                    currentProcess = queue.poll();
                }
            }

            if (currentProcess == null && !queue.isEmpty()) {
                currentProcess = queue.poll();
            }

            if (currentProcess != null) {
                int start = currentTime;

                currentProcess.remainingTime--;
                currentTime++;

                Map<String, Object> event = new HashMap<>();
                event.put("process", "P" + currentProcess.id);
                event.put("start", start);
                event.put("end", currentTime);
                timeline.add(event);

                if (currentProcess.remainingTime == 0) {
                    turnaroundTime.put(currentProcess.id, currentTime - currentProcess.arrivalTime);
                    waitingTime.put(currentProcess.id, turnaroundTime.get(currentProcess.id) - currentProcess.burstTime);
                    scheduledOrder.add(currentProcess);
                    currentProcess = null;
                }
            } else if (index < processesCopy.size()) {
                int idleStart = currentTime;
                currentTime = processesCopy.get(index).arrivalTime;

                Map<String, Object> idleEvent = new HashMap<>();
                idleEvent.put("process", "IDLE");
                idleEvent.put("start", idleStart);
                idleEvent.put("end", currentTime);
                timeline.add(idleEvent);
            } else {
                break;
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
        results.put("algorithm", "Shortest Remaining Time Next (SRTN)");
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