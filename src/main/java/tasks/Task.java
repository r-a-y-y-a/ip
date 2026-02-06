package tasks;

/**
 * Task is an abstract base class representing a task in the task management system.
 * It provides common functionality for all task types (Todo, Deadline, Event).
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class Task {
    private String task;
    private boolean done;
    private final String type;

    /**
     * Constructs a Task with the specified type, description, and completion status.
     *
     * @param type the type of task (e.g., "t" for Todo, "d" for Deadline, "e" for Event)
     * @param task the description of the task
     * @param done whether the task is marked as completed
     */
    public Task(String type, String task, boolean done) {
        this.task = task;
        this.done = done;
        this.type = type;
    }

    /**
     * Returns the type of this task.
     *
     * @return the task type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the description of this task.
     *
     * @return the task description
     */
    public String getTask() {
        return this.task;
    }

    /**
     * Marks this task as completed.
     */
    public void mark() {
        this.done = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void unmark() {
        this.done = false;
    }

    /**
     * Checks if this task is marked as completed.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return this.done;
    }
}

