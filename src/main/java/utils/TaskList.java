package utils;
import java.util.ArrayList;

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
}
