package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Deadline represents a task with a deadline date/time.
 * It extends Task and provides functionality specific to deadline tasks.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
    private final LocalDateTime deadline;

    /**
     * Constructs a Deadline with the specified task description,
     * completion status, and deadline date/time.
     *
     * @param task the description of the task
     * @param done whether the task is marked as completed
     * @param deadline the deadline date/time for this task
     */
    public Deadline(String task, boolean done, LocalDateTime deadline) {
        super("d", task, done);
        this.deadline = deadline;
    }

    /**
     * Returns the deadline date/time of this task.
     *
     * @return the deadline date/time
     */
    public LocalDateTime getDeadline() {
        return this.deadline;
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
        String out = "[D]";
        if (this.isDone()) {
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.getTask() + "(by: " + deadline.format(OUTPUT_FORMATTER) + "hrs)";
        return out;
    }
}
