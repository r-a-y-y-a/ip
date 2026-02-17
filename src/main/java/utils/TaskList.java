package utils;
import java.util.ArrayList;
import java.time.LocalDateTime;

import tasks.Task;
import tasks.Deadline;
import tasks.Event;

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
    }

    /**
     * Constructs a TaskList initialized with the given list of tasks.
     *
     * @param tasks the initial collection of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to be added
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes a task from the list at the specified index.
     *
     * @param index the index of the task to be removed
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void remove(int index) {
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
        return this.tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Retrieves all tasks in the list.
     *
     * @return the list of all tasks
     */
    public ArrayList<Task> getAll() {
        return this.tasks;
    }

    /**
     * Returns a string representation of the list with numbered entries.
     *
     * @return a formatted string representation of all tasks
     */
    public String toString() {
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
        ArrayList<Task> upcoming = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limit = now.plusDays(7);
        for (Task t : this.tasks) {
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
