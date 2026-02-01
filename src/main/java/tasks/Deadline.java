package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final LocalDateTime deadline;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    public Deadline (String task, boolean done, LocalDateTime deadline){
        super("d", task, done);
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    public static LocalDateTime parseDate(String dateStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateStr, INPUT_FORMATTER);
    }

    @Override
    public String toString() {
        String out = "[D]";
        if (this.done){
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.task + "(by: " + deadline.format(OUTPUT_FORMATTER) + ")";
        return out;
    }
}
