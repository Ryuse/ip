public class Deadline extends Task  {
    String timeEnd;
    public Deadline(String name, String timeEnd) {
        super(name);
        this.timeEnd = timeEnd;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getType(){
        return "D";
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + timeEnd + ")";
    }

    public String getSaveText() {
        return super.getSaveText() +  "|" + timeEnd;
    }
}
