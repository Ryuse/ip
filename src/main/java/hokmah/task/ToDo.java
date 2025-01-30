package hokmah.task;

public class ToDo extends Task  {
    /**
     * Constructs a Todo task.
     * @param name Task description
     */
    public ToDo(String name) {
        super(name);
    }

    /**
     * Returns task type identifier.
     * @return "T" for Todo tasks
     */
    public String getType(){
        return "T";
    }

    /**
     * Returns formatted string representation.
     * @return String with task details
     */
    public String toString() {
        return "[T]" + super.toString();
    }
}
