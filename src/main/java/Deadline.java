public class Deadline extends Task  {
    String timeEnd;
    public Deadline(String name, String timeEnd) {
        super(name);
        this.timeEnd = timeEnd;
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + timeEnd + ")";
    }
}
