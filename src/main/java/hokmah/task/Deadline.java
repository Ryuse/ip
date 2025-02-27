package hokmah.task;

import static hokmah.Hokmah.DATETIME_OUTPUT_FORMAT;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import hokmah.Hokmah;


/**
 * Concrete task type with deadline constraint.
 * Represents tasks requiring completion by specific date/time.
 */
public class Deadline extends Task {
    private LocalDateTime timeEnd;

    /**
     * Constructs a Deadline task.
     *
     * @param name    Task description
     * @param timeEnd Deadline date/time
     */
    public Deadline(String name, LocalDateTime timeEnd) {
        super(name);
        this.timeEnd = timeEnd;
    }

    /**
     * Gets the deadline date/time.
     *
     * @return LocalDateTime of deadline
     */
    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }


    /**
     * Returns task type identifier.
     *
     * @return "D" for Deadline tasks
     */
    public String getType() {
        return "D";
    }

    /**
     * Returns formatted string representation.
     *
     * @return String with task details and formatted deadline
     */
    public String toString() {
        return "[D]" + super.toString()
                + " (by: "
                + timeEnd.format(DateTimeFormatter.ofPattern(DATETIME_OUTPUT_FORMAT))
                + ")";
    }

    /**
     * Generates save-friendly text representation.
     *
     * @return Pipe-separated values including deadline time
     */
    public String getSaveText() {
        return super.getSaveText()
                + "|"
                + timeEnd.format(DateTimeFormatter.ofPattern(Hokmah.DATETIME_INPUT_FORMAT));
    }
}
