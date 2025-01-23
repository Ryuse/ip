public class Event extends Task  {
    String timeStart;
    String timeEnd;

    public Event(String name, String timeStart, String timeEnd) {
        super(name);
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String toString() {
        return "[E]" + super.toString() + " (from: " + timeStart + " to: " + timeEnd + ")";
    }
}
