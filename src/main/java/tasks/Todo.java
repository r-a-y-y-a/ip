package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Todo extends Task {
    public Todo (String task, boolean done){
        super("t", task, done);
    }
    @Override
    public String toString() {
        String out = "[T]";
        if (this.done){
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.task;
        return out;
    }
}

