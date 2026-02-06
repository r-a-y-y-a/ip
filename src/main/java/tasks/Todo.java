package tasks;

/**
 * Todo represents a simple task without a specific deadline or time span.
 * It extends Task and provides functionality specific to todo tasks.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class Todo extends Task {
    /**
     * Constructs a Todo with the specified task description and completion status.
     *
     * @param task the description of the todo task
     * @param done whether the task is marked as completed
     */
    public Todo(String task, boolean done) {
        super("t", task, done);
    }

    @Override
    public String toString() {
        String out = "[T]";
        if (this.isDone()) {
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.getTask();
        return out;
    }
}

