package hokmah.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import hokmah.Hokmah;


/**
 * Concrete task type with time-bound duration.
 * Represents activities occurring between specific start/end times.
 */
public class Event extends Task {
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    /**
     * Constructs an Event task with specified time range.
     *
     * @param name      Task description
     * @param timeStart Event start date/time
     * @param timeEnd   Event end date/time
     */
    public Event(String name, LocalDateTime timeStart, LocalDateTime timeEnd) {
        super(name);
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    /**
     * Gets the event start time.
     *
     * @return LocalDateTime of event start
     */
    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    /**
     * Gets the event end time.
     *
     * @return LocalDateTime of event end
     */
    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }


    /**
     * Returns task type identifier.
     *
     * @return "E" for Event tasks
     */
    public String getType() {
        return "E";
    }

    /**
     * Returns formatted string representation.
     *
     * @return String with task details and formatted time range
     */
    public String toString() {
        return "[E]" + super.toString()
                + " (from: "
                + timeStart.format(DateTimeFormatter.ofPattern(DATE_STRING_OUTPUT_FORMAT))
                + " to: "
                + timeEnd.format(DateTimeFormatter.ofPattern(DATE_STRING_OUTPUT_FORMAT))
                + ")";
    }

    /**
     * Generates save-friendly text representation.
     *
     * @return Pipe-separated values including event times
     */
    public String getSaveText() {
        return super.getSaveText() + "|"
                + timeStart.format(DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT))
                + "|"
                + timeEnd.format(DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT));
    }

}
