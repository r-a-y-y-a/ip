package utils;
import java.time.LocalDateTime;
import java.util.ArrayList;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;

/**
 * TaskList is a wrapper class for managing a collection of Task objects.
 * It provides methods to add, remove, retrieve, and query tasks in the list.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        assert this.tasks != null : "tasks list must be initialized";
    }

    /**
     * Constructs a TaskList initialized with the given list of tasks.
     *
     * @param tasks the initial collection of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "tasks parameter must not be null";
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to be added
     */
    public void add(Task task) {
        assert task != null : "task to add must not be null";
        this.tasks.add(task);
    }

    /**
     * Removes a task from the list at the specified index.
     *
     * @param index the index of the task to be removed
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void remove(int index) {
        assert index >= 0 && index < this.tasks.size() : "remove index out of range";
        this.tasks.remove(index);
    }

    /**
     * Retrieves a task at the specified index from the list.
     *
     * @param index the index of the task to retrieve
     * @return the task at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task get(int index) {
        assert index >= 0 && index < this.tasks.size() : "get index out of range";
        return this.tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        assert this.tasks != null : "tasks list must not be null";
        return this.tasks.size();
    }

    /**
     * Retrieves all tasks in the list.
     *
     * @return the list of all tasks
     */
    public ArrayList<Task> getAll() {
        assert this.tasks != null : "tasks list must not be null";
        return this.tasks;
    }

    /**
     * Returns a string representation of the list with numbered entries.
     *
     * @return a formatted string representation of all tasks
     */
    public String toString() {
        assert this.tasks != null : "tasks list must not be null";
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            out.append(i + 1).append(".").append(tasks.get(i).toString()).append("\n");
        }
        return out.toString();
    }

    /**
     * Returns a list of tasks that have a deadline or event end within the next 7 days.
     * Both deadlines and events are considered. Completed tasks are ignored.
     *
     * @return an ArrayList of upcoming tasks within the next 7 days
     */
    public ArrayList<Task> getUpcomingWithinWeek() {
        assert this.tasks != null : "tasks list must not be null";
        ArrayList<Task> upcoming = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limit = now.plusDays(7);
        for (Task t : this.tasks) {
            assert t != null : "task in list must not be null";
            if (t.isDone()) {
                continue;
            }
            if (t instanceof Deadline) {
                LocalDateTime dl = ((Deadline) t).getDeadline();
                if ((dl.isAfter(now) || dl.isEqual(now)) && (dl.isBefore(limit) || dl.isEqual(limit))) {
                    upcoming.add(t);
                }
            } else if (t instanceof Event) {
                LocalDateTime end = ((Event) t).getEnd();
                if ((end.isAfter(now) || end.isEqual(now)) && (end.isBefore(limit) || end.isEqual(limit))) {
                    upcoming.add(t);
                }
            }
        }
        return upcoming;
    }
}
