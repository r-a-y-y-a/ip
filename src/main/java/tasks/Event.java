package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    public Event (String task, boolean done, LocalDateTime start, LocalDateTime end){
        super("e", task, done);
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart(){
        return this.start;
    }

    public LocalDateTime getEnd(){
        return this.end;
    }

    public static LocalDateTime parseDate(String dateStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateStr, INPUT_FORMATTER);
    }

    @Override
    public String toString() {
        String out = "[E]";
        if (this.done){
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.task + "(from: " + this.start.format(OUTPUT_FORMATTER) + " to: " + this.end.format(OUTPUT_FORMATTER) + ")";
        return out;
    }
}
