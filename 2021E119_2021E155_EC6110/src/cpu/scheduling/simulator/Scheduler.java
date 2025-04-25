package cpu.scheduling.simulator;
import java.util.*;

interface Scheduler {
    void schedule(List<Process> processes);
    Map<String, Object> getResults();
    List<Map<String, Object>> getTimeline();
}