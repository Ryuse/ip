package hokmah.task;

import hokmah.Hokmah;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    LocalDateTime timeEnd;

    public Deadline(String name, LocalDateTime timeEnd) {
        super(name);
        this.timeEnd = timeEnd;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public String getType() {
        return "D";
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + timeEnd.format(DateTimeFormatter.ofPattern(DATE_STRING_OUTPUT_FORMAT)) + ")";
    }

    public String getSaveText() {
        return super.getSaveText() +
                "|" +
                timeEnd.format(DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT));
    }
}
