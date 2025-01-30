package hokmah.task;

import hokmah.Hokmah;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    LocalDateTime timeStart;
    LocalDateTime timeEnd;

    public Event(String name, LocalDateTime timeStart, LocalDateTime timeEnd) {
        super(name);
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public LocalDateTime getTimeEnd() {
        return timeEnd;
    }

    public String getType() {
        return "E";
    }

    public String toString() {
        return "[E]" + super.toString() +
                " (from: " + timeStart.format(DateTimeFormatter.ofPattern(DATE_STRING_OUTPUT_FORMAT)) +
                " to: " + timeEnd.format(DateTimeFormatter.ofPattern(DATE_STRING_OUTPUT_FORMAT)) + ")";
    }

    public String getSaveText() {
        return super.getSaveText() + "|"
                + timeStart.format(DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT))
                + "|"
                + timeEnd.format(DateTimeFormatter.ofPattern(Hokmah.DATE_TIME_FORMAT));
    }

}
