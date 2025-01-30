package hokmah.task;


/**
 * Concrete task type without time constraints.
 * Represents simple actionable items requiring completion.
 */
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
