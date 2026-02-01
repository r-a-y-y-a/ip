package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {
    String task;
    boolean done;
    final String type;
    public Task(String type, String task, boolean done){
        this.task = task;
        this.done = done;
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
    public String getTask(){
        return this.task;
    }
    public void mark() {
        this.done = true;
    }
    public void unmark() {
        this.done = false;
    }
    public boolean checkDone(){
        return this.done;
    }
}

