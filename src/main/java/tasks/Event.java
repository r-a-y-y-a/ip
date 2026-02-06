package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Event represents a task that spans from a start date/time to an end date/time.
 * It extends Task and provides functionality specific to event tasks.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Constructs an Event with the specified task description, completion status,
     * and start and end date/times.
     *
     * @param task the description of the event
     * @param done whether the event is marked as completed
     * @param start the start date/time of the event
     * @param end the end date/time of the event
     */
    public Event(String task, boolean done, LocalDateTime start, LocalDateTime end) {
        super("e", task, done);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start date/time of this event.
     *
     * @return the start date/time
     */
    public LocalDateTime getStart() {
        return this.start;
    }

    /**
     * Returns the end date/time of this event.
     *
     * @return the end date/time
     */
    public LocalDateTime getEnd() {
        return this.end;
    }

    /**
     * Parses a date string in the format "dd-MM-yyyy HHmm" to a LocalDateTime object.
     *
     * @param dateStr the date string to parse
     * @return the parsed LocalDateTime
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime parseDate(String dateStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateStr, INPUT_FORMATTER);
    }

    @Override
    public String toString() {
        String out = "[E]";
        if (this.isDone()) {
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.getTask() + "(from: " + this.start.format(OUTPUT_FORMATTER)
                + " to: " + this.end.format(OUTPUT_FORMATTER) + ")";
        return out;
    }
}
