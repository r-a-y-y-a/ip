package utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import exceptions.EmptyTaskException;
import exceptions.FishballException;
import exceptions.InvalidCommandException;
import exceptions.InvalidIndexException;
import exceptions.MissingParameterException;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

/**
 * UI is responsible for handling all user interface operations including displaying messages
 * and processing user input for the Fishball task management application.
 * It manages the display of tasks, confirmations, errors, and coordinates command execution.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class UI {
    public static final String HORIZONTAL_LINE = "____________________________________________________________\n";
    public static final String EXIT_MESSAGE = "Bye. Hope to see you again soon!\n";
    public static final String INDENT = "     ";
    public static final String WELCOME_MESSAGE = "Hello, I'm Fishball!\n" + INDENT + "What can I do for you?\n";

    /**
     * Displays the welcome message when the application starts.
     */
    public void printWelcome() {
        System.out.println(INDENT
                + HORIZONTAL_LINE
                + INDENT
                + WELCOME_MESSAGE
                + INDENT
                + HORIZONTAL_LINE);
    }

    /**
     * Displays the exit message when the application terminates.
     */
    public void printExit() {
        System.out.println(INDENT
                + HORIZONTAL_LINE
                + INDENT
                + EXIT_MESSAGE
                + INDENT
                + HORIZONTAL_LINE);
    }

    /**
     * Displays all tasks in the provided task list.
     *
     * @param record the TaskList to be displayed
     */
    public void printList(TaskList record) {
        System.out.print(INDENT + HORIZONTAL_LINE);
        System.out.println(INDENT + "Here are the tasks in your list:");
        for (int i = 0; i < record.size(); i++) {
            System.out.print(INDENT + (i + 1) + ".");
            System.out.println(record.get(i));
        }
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    /**
     * Finds and displays tasks whose descriptions contain the given keyword as a whole word.
     * Matching is case-insensitive; substrings inside other words are not considered matches.
     * Example: keyword "sub" matches "find a sub for james" but not "subway".
     *
     * @param keyword the keyword to search for (single word)
     * @param record the TaskList to search
     */
    public void find(String keyword, TaskList record) {
        System.out.print(INDENT + HORIZONTAL_LINE);
        System.out.println(INDENT + "Here are the matching tasks in your list:");
        for (int i = 0; i < record.size(); i++) {
            Task t = record.get(i);
            String[] desc = t.getTask().split(" ");
            if (java.util.Arrays.asList(desc).contains(keyword)) {
                System.out.print(INDENT + " " + (i + 1) + ".");
                System.out.println(t);
                continue;
            }
        }
        System.out.println(INDENT + HORIZONTAL_LINE);
    }

    /**
     * Displays a confirmation message when a task is successfully added.
     *
     * @param task the task that was added
     * @param totalTasks the total number of tasks after addition
     */
    public void printTaskAdded(Task task, int totalTasks) {
        System.out.println(INDENT
                + HORIZONTAL_LINE
                + INDENT
                + "Got it. I've added this task: \n"
                + INDENT
                + "    "
                + task);
        System.out.println(INDENT + "Now you have " + totalTasks + " tasks in the list.\n" + INDENT + HORIZONTAL_LINE);
    }

    /**
     * Displays a confirmation message when a task is successfully deleted.
     *
     * @param task the task that was deleted
     * @param totalTasks the total number of tasks after deletion
     */

    public void printTaskDeleted(Task task, int totalTasks) {
        System.out.println(INDENT + HORIZONTAL_LINE + INDENT
                + "Noted. I've removed this task:\n"
                + INDENT
                + "  " + task);
        System.out.println(INDENT + "Now you have " + totalTasks + " tasks in the list.\n" + INDENT + HORIZONTAL_LINE);
    }

    /**
     * Displays a confirmation message when a task is marked as completed.
     *
     * @param task the task that was marked as done
     */
    public void printTaskMarked(Task task) {
        System.out.println(INDENT
                + HORIZONTAL_LINE
                + INDENT
                + "Nice! I've marked this task as done\n"
                + INDENT
                + task
                + '\n'
                + INDENT
                + HORIZONTAL_LINE);
    }

    /**
     * Displays a confirmation message when a task is marked as not completed.
     *
     * @param task the task that was marked as not done
     */
    public void printTaskUnmarked(Task task) {
        System.out.println(INDENT
                + HORIZONTAL_LINE
                + INDENT
                + "Ok, I've marked this task as not done yet\n"
                + INDENT
                + task
                + '\n'
                + INDENT
                + HORIZONTAL_LINE);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to be displayed
     */
    public void printError(String message) {
        System.out.println(INDENT
                + HORIZONTAL_LINE
                + INDENT
                + "OOPS! Come on fishball! "
                + message
                + "\n"
                + INDENT
                + HORIZONTAL_LINE);
    }

    /**
     * Processes a single user command and returns a string response.
     * Unlike handleInput, this method does not loop and returns a response string
     * suitable for GUI use.
     *
     * @param input the user's command input
     * @param record the TaskList to be modified by user commands
     * @param storage the Storage instance for persisting changes
     * @return a response message indicating the result of the command
     */
    public String processCommand(String input, TaskList record, Storage storage) {
        try {
            input = input.trim();
            if (input.isEmpty()) {
                return "OOPS! Come on fishball! This is an empty input! Please enter a command!";
            }
            String[] parse = input.split(" ");
            String command = parse[0];
            Task curr;

            if (command.equals("bye") && parse.length == 1) {
                return EXIT_MESSAGE;
            }

            if (command.equals("list")) {
                if (parse.length != 1) {
                    return "OOPS! Come on fishball! The list command does not take any parameters! "
                            + "Just type 'list' to see your tasks.";
                }
                StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
                for (int i = 0; i < record.size(); i++) {
                    sb.append((i + 1)).append(". ").append(record.get(i)).append("\n");
                }
                return sb.toString();
            } else if (command.equals("find")) {
                if (parse.length < 2) {
                    return "OOPS! Come on fishball! Please provide a keyword in the format: find <keyword>";
                }
                String keyword = parse[1];
                StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
                boolean found = false;
                for (int i = 0; i < record.size(); i++) {
                    Task t = record.get(i);
                    String[] desc = t.getTask().split(" ");
                    if (java.util.Arrays.asList(desc).contains(keyword)) {
                        sb.append((i + 1)).append(". ").append(t).append("\n");
                        found = true;
                    }
                }
                if (!found) {
                    return "No matching tasks found.";
                }
                return sb.toString();
            } else if (command.equals("delete")) {
                if (parse.length != 2) {
                    return "OOPS! Come on fishball! Please provide a delete in the format: delete <task number>";
                }
                try {
                    int index = Integer.parseInt(parse[1].trim()) - 1;
                    if (index < 0 || index >= record.size()) {
                        return "OOPS! Come on fishball! Task number is out of range! Please provide a valid task number.";
                    }
                    Task delete = record.get(index);
                    record.remove(index);
                    storage.store(record.getAll());
                    return "Noted. I've removed this task:\n  " + delete
                            + "\nNow you have " + record.size() + " tasks in the list.";
                } catch (NumberFormatException e) {
                    return "OOPS! Come on fishball! Please provide a delete in the format: delete <task number>";
                }
            } else if (command.equals("mark") && parse.length == 2) {
                try {
                    int index = Integer.parseInt(parse[1]) - 1;
                    if (index < 0 || index >= record.size()) {
                        return "OOPS! Come on fishball! Task number is out of range! Please provide a valid task number.";
                    }
                    record.get(index).mark();
                    storage.store(record.getAll());
                    return "Nice! I've marked this task as done\n" + record.get(index);
                } catch (NumberFormatException e) {
                    return "OOPS! Come on fishball! Please provide a valid task number.";
                }
            } else if (command.equals("unmark") && parse.length == 2) {
                try {
                    int index = Integer.parseInt(parse[1]) - 1;
                    if (index < 0 || index >= record.size()) {
                        return "OOPS! Come on fishball! Task number is out of range! Please provide a valid task number.";
                    }
                    record.get(index).unmark();
                    storage.store(record.getAll());
                    return "Ok, I've marked this task as not done yet\n" + record.get(index);
                } catch (NumberFormatException e) {
                    return "OOPS! Come on fishball! Please provide a valid task number.";
                }
            } else {
                String taskType = parse[0];
                if (taskType.equals("todo")) {
                    String task = "";
                    for (int i = 1; i < parse.length; i++) {
                        task = task + parse[i] + " ";
                    }
                    task = task.trim();
                    if (task.isEmpty()) {
                        return "OOPS! Come on fishball! The description of a todo cannot be empty!";
                    }
                    curr = new Todo(task, false);
                    record.add(curr);
                    storage.store(record.getAll());
                    return "Got it. I've added this task: \n    " + curr
                            + "\nNow you have " + record.size() + " tasks in the list.";
                } else if (taskType.equals("deadline")) {
                    String task = "";
                    String deadline = "";
                    boolean hasBy = false;
                    for (int i = 1; i < parse.length; i++) {
                        if (parse[i].equals("/by")) {
                            hasBy = true;
                            for (int j = i + 1; j < parse.length; j++) {
                                if (j == parse.length - 1) {
                                    deadline = deadline + parse[j];
                                    break;
                                }
                                deadline = deadline + parse[j] + " ";
                            }
                            break;
                        }
                        task = task + parse[i] + " ";
                    }
                    task = task.trim();
                    deadline = deadline.trim();
                    if (task.isEmpty() || !hasBy || deadline.isEmpty()) {
                        return "OOPS! Come on fishball! Please provide a deadline in the format: "
                                + "deadline <task> /by <dd-MM-yyyy HHmm>";
                    }
                    try {
                        LocalDateTime deadlineDate = Deadline.parseDate(deadline);
                        curr = new Deadline(task, false, deadlineDate);
                        record.add(curr);
                        storage.store(record.getAll());
                        return "Got it. I've added this task: \n    " + curr
                                + "\nNow you have " + record.size() + " tasks in the list.";
                    } catch (DateTimeParseException e) {
                        return "OOPS! Come on fishball! Please provide a deadline in the format: "
                                + "deadline <task> /by <dd-MM-yyyy HHmm> (e.g., 2019-10-15 1400)";
                    }
                } else if (taskType.equals("event")) {
                    String task = "";
                    String start = "";
                    String end = "";
                    boolean hasFrom = false;
                    boolean hasTo = false;
                    for (int i = 1; i < parse.length; i++) {
                        if (parse[i].equals("/from")) {
                            hasFrom = true;
                            i++;
                            while (i < parse.length && !parse[i].equals("/to")) {
                                if (i == parse.length - 1
                                        || (i + 1 < parse.length && parse[i + 1].equals("/to"))) {
                                    start = start + parse[i];
                                    i++;
                                    break;
                                }
                                start = start + parse[i] + " ";
                                i++;
                            }
                        }
                        if (parse[i].equals("/to")) {
                            hasTo = true;
                            for (int j = i + 1; j < parse.length; j++) {
                                if (j == parse.length - 1) {
                                    end = end + parse[j];
                                    break;
                                }
                                end = end + parse[j] + " ";
                            }
                            break;
                        }
                        task = task + parse[i] + " ";
                    }
                    task = task.trim();
                    start = start.trim();
                    end = end.trim();
                    if (task.isEmpty() || !hasFrom || start.isEmpty() || !hasTo || end.isEmpty()) {
                        return "OOPS! Come on fishball! Please provide an event in the format: "
                                + "event <task> /from <dd-MM-yyyy HHmm> /to <dd-MM-yyyy HHmm>";
                    }
                    try {
                        LocalDateTime startDate = Event.parseDate(start);
                        LocalDateTime endDate = Event.parseDate(end);
                        curr = new Event(task, false, startDate, endDate);
                        record.add(curr);
                        storage.store(record.getAll());
                        return "Got it. I've added this task: \n    " + curr
                                + "\nNow you have " + record.size() + " tasks in the list.";
                    } catch (DateTimeParseException e) {
                        return "OOPS! Come on fishball! Please provide an event in the format: "
                                + "event <task> /from <dd-MM-yyyy HHmm> /to <dd-MM-yyyy HHmm>";
                    }
                } else {
                    return "OOPS! Come on fishball! I don't recognize that command. Please use: "
                            + "todo, deadline, event, list, find, mark, unmark, or bye.";
                }
            }
        } catch (Exception e) {
            return "OOPS! Come on fishball! " + e.getMessage();
        }
    }

    /**
     * Handles user input in a loop, processing commands and updating tasks accordingly.
     * Supports commands: list, delete, mark, unmark, todo, deadline, event, and bye.
     *
     * @param record the TaskList to be modified by user commands
     * @param storage the Storage instance for persisting changes
     * @throws FishballException if an error occurs during input processing
     */
    public void handleInput(TaskList record, Storage storage) throws FishballException {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = scanner.nextLine();
                String response = processCommand(input, record, storage);
                if (response == null) {
                    // defensive: should not happen, but continue loop
                    continue;
                }
                if (response.equals(EXIT_MESSAGE)) {
                    printExit();
                    return;
                }
                if (response.startsWith("OOPS!")) {
                    printError(response.replaceFirst("OOPS! Come on fishball! ", ""));
                } else {
                    System.out.println(INDENT + HORIZONTAL_LINE + INDENT + response + INDENT + HORIZONTAL_LINE);
                }
            }
        }
    }
}
