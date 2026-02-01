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


public class UI {
    public static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    public static final String EXIT_MESSAGE = "Bye. Hope to see you again soon!\n";
    public static final String INDENT = "     ";
    public static final String WELCOME_MESSAGE = "Hello, I'm Fishball!\n" + INDENT + "What can I do for you?\n";

    public void printWelcome() {
        System.out.println(INDENT + HORIZONTAL_LINE + INDENT + WELCOME_MESSAGE +
                INDENT + HORIZONTAL_LINE);
    }

    public void printExit() {
        System.out.println(INDENT + HORIZONTAL_LINE +
                INDENT + EXIT_MESSAGE +
                INDENT + HORIZONTAL_LINE);
    }

    public void printList(TaskList record) {
        System.out.print(INDENT + HORIZONTAL_LINE);
        System.out.println(INDENT + "Here are the tasks in your list:");
        for (int i = 0; i < record.size(); i++) {
            System.out.print(INDENT + (i + 1) + ".");
            System.out.println(record.get(i));
        }
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    public void printTaskAdded(Task task, int totalTasks) {
        System.out.println(INDENT + HORIZONTAL_LINE +
                INDENT + "Got it. I've added this task: \n" +
                INDENT + "    " + task);
        System.out.println(INDENT + "Now you have " + totalTasks + " tasks in the list.\n" + INDENT + HORIZONTAL_LINE);
    }

    public void printTaskDeleted(Task task, int totalTasks) {
        System.out.println(INDENT + HORIZONTAL_LINE + INDENT + "Noted. I've removed this task:\n" + INDENT + "  " + task);
        System.out.println(INDENT + "Now you have " + totalTasks + " tasks in the list.\n" + INDENT + HORIZONTAL_LINE);
    }

    public void printTaskMarked(Task task) {
        System.out.println(INDENT + HORIZONTAL_LINE +
                INDENT + "Nice! I've marked this task as done\n" +
                INDENT + task + '\n' + INDENT + HORIZONTAL_LINE);
    }

    public void printTaskUnmarked(Task task) {
        System.out.println(INDENT + HORIZONTAL_LINE +
                INDENT + "Ok, I've marked this task as not done yet\n" +
                INDENT + task + '\n' + INDENT + HORIZONTAL_LINE);
    }

    public void printError(String message) {
        System.out.println(INDENT + HORIZONTAL_LINE + INDENT + "OOPS! Come on fishball! " + message + "\n" + INDENT + HORIZONTAL_LINE);
    }
}
