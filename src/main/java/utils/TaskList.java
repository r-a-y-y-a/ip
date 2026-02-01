package utils;
import exceptions.EmptyTaskException;
import exceptions.FishballException;
import exceptions.InvalidCommandException;
import exceptions.InvalidIndexException;
import exceptions.MissingParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void add(Task task) {
        this.tasks.add(task);
    }

    public void remove(int index) {
        this.tasks.remove(index);
    }

    public Task get(int index) {
        return this.tasks.get(index);
    }

    public int size() {
        return this.tasks.size();
    }

    public ArrayList<Task> getAll() {
        return this.tasks;
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            out.append(i + 1).append(".").append(tasks.get(i).toString()).append("\n");
        }
        return out.toString();
    }
}