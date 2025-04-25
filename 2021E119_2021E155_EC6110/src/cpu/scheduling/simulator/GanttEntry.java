package cpu.scheduling.simulator;

public class GanttEntry {
    int processId;
    int startTime;
    int endTime;

    public GanttEntry(int processId, int startTime, int endTime) {
        this.processId = processId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}