package hokmah.task;

import java.time.LocalDateTime;


public class Task {
    public static final String DATE_STRING_OUTPUT_FORMAT = "MMM d yyyy hh:mm";
    private boolean isDone = false;
    private final String name;

    /**
     * Constructs a Task with the specified name.
     * @param name The name/description of the task
     */
    public Task(String name) {
        this.name = name;
    }

    /**
     * Marks the task as completed.
     */
    public void markDone() {
        isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void unmarkDone() {
        isDone = false;
    }

    /**
     * Returns the task type identifier.
     * @return String representing task type (to be overridden by subclasses)
     */
    public String getType(){
        return this.getClass().getName();
    }

    /**
     * Checks if the task is completed.
     * @return true if task is marked done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Gets the task name.
     * @return The name/description of the task
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the start time (for event tasks).
     * @return null for base Task class (to be overridden by subclasses)
     */
    public LocalDateTime getTimeStart() {
        return null;
    }

    /**
     * Gets the end time (for deadline/event tasks).
     * @return null for base Task class (to be overridden by subclasses)
     */
    public LocalDateTime getTimeEnd() {
        return null;
    }

    /**
     * Returns formatted string representation of the task.
     * @return String showing completion status and name
     */
    public String toString() {
        String output = "";
        if (isDone) {
            output += "[X] ";
        }
        else{
            output += "[ ] ";
        }
        output += name;
        return output;
    }

    /**
     * Generates save-friendly text representation.
     * @return Pipe-separated values for storage
     */
    public String getSaveText() {
        String output = "";
        output += getType() + "|";
        if (isDone) {
            output += "1";
        }
        else{
            output += "0";
        }
        output += "|" + name;

        return output.trim();
    }


}
