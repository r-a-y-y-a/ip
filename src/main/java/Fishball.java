import exceptions.EmptyTaskException;
import exceptions.FishballException;
import exceptions.InvalidCommandException;
import exceptions.InvalidIndexException;
import exceptions.MissingParameterException;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;

class Storage {
    private File f;

    public Storage(String filepath){
        try {
            this.f = new File(filepath);
            File directory = f.getParentFile();
            if (directory != null && !directory.exists()) {
                directory.mkdirs();
            }
            if (!f.exists()){
                f.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public ArrayList<Task> load(){
        ArrayList<Task> record = new ArrayList<>();
        try (Scanner s = new Scanner(f)) {
            while (s.hasNext()) {
                String[] parse = s.nextLine().split(",");
                String type = parse[0];
                if (type.equals("d")){
                    LocalDateTime deadlineDate = LocalDateTime.parse(parse[3]);
                    record.add(new Deadline(parse[1], Boolean.parseBoolean(parse[2]), deadlineDate));
                } else if (type.equals("e")) {
                    LocalDateTime startDate = LocalDateTime.parse(parse[3]);
                    LocalDateTime endDate = LocalDateTime.parse(parse[4]);
                    record.add(new Event(parse[1], Boolean.parseBoolean(parse[2]), startDate, endDate));
                } else {
                    record.add(new Todo(parse[1], Boolean.parseBoolean(parse[2])));
                }
            }
        } catch (IOException e){
            System.out.println("Error");
        }
        return record;
    }

    public void store(ArrayList<Task> updatedTasks) {
        String line;
        // Update tasks list
        try (FileWriter fw = new FileWriter(f, false)) {
            for (int i = 0; i < updatedTasks.size(); i++){
                Task t = updatedTasks.get(i);
                if (t.getType().equals("d")){
                    Deadline d = (Deadline) updatedTasks.get(i);
                    line = d.getType() + "," + d.getTask() + "," + d.checkDone() + "," + d.getDeadline();
                } else if (t.getType().equals("e")) {
                    Event e = (Event) updatedTasks.get(i);
                    line = e.getType() + "," + e.getTask() + "," + e.checkDone() + "," + e.getStart() + "," + e.getEnd();
                } else {
                    line = t.getType() + "," + t.getTask() + "," + t.checkDone();
                }
                fw.write(line + "\n");
            }
            fw.flush();
        } catch (IOException e){
            System.out.println("Exception writing to file");
        }
    }
}

class TaskList {
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

class UI {
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

public class Fishball {
    public static void main(String[] args) throws FishballException {
        Storage s = new Storage("../../../data/fishball.txt");
        TaskList record = new TaskList(s.load());
        UI ui = new UI();

        ui.printWelcome();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    String input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        throw new InvalidCommandException("This is an empty input! Please enter a command!");
                    }
                    String[] parse = input.split(" ");
                    String command = parse[0];
                    Task curr;

                    if (command.equals("bye") && parse.length == 1) {
                        ui.printExit();
                        return;
                    }

                    if (command.equals("list")) {
                        if (parse.length != 1) {
                            throw new InvalidCommandException("The list command does not take any parameters! Just type 'list' to see your tasks.");
                        }
                        ui.printList(record);
                    } else if (command.equals("delete")) {
                        if (parse.length != 2) {
                            throw new MissingParameterException("Please provide a delete in the format: delete <task number>");
                        }
                        try {
                            int index = Integer.parseInt(parse[1].trim()) - 1;
                            if (index < 0 || index >= record.size()) {
                                throw new InvalidIndexException("Task number is out of range! Please provide a valid task number.");
                            }
                            Task delete = record.get(index);
                            record.remove(index);
                            ui.printTaskDeleted(delete, record.size());
                        } catch (NumberFormatException e) {
                            throw new MissingParameterException("Please provide a delete in the format: delete <task number>");
                        }
                    } else if (command.equals("mark") && parse.length == 2) {
                        try {
                            int index = Integer.parseInt(parse[1]) - 1;
                            if (index < 0 || index >= record.size()) {
                                throw new InvalidIndexException("Task number is out of range! Please provide a valid task number.");
                            }
                            record.get(index).mark();
                            ui.printTaskMarked(record.get(index));
                        } catch (NumberFormatException e) {
                            throw new MissingParameterException("Please provide a valid task number.");
                        }
                    } else if (command.equals("unmark") && parse.length == 2) {
                        try {
                            int index = Integer.parseInt(parse[1]) - 1;
                            if (index < 0 || index >= record.size()) {
                                throw new InvalidIndexException("Task number is out of range! Please provide a valid task number.");
                            }
                            record.get(index).unmark();
                            ui.printTaskUnmarked(record.get(index));
                        } catch (NumberFormatException e) {
                            throw new MissingParameterException("Please provide a valid task number.");
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
                                throw new EmptyTaskException("The description of a todo cannot be empty!");
                            }
                            curr = new Todo(task, false);
                            record.add(curr);
                            ui.printTaskAdded(curr, record.size());
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
                                throw new MissingParameterException("Please provide a deadline in the format: deadline <task> /by <dd-MM-yyyy HHmm>");
                            }
                            try {
                                LocalDateTime deadlineDate = Deadline.parseDate(deadline);
                                curr = new Deadline(task, false, deadlineDate);
                                record.add(curr);
                                ui.printTaskAdded(curr, record.size());
                            } catch (DateTimeParseException e) {
                                throw new MissingParameterException("Please provide a deadline in the format: deadline <task> /by <dd-MM-yyyy HHmm> (e.g., 2019-10-15 1400)");
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
                                        if (i == parse.length - 1 || (i + 1 < parse.length && parse[i + 1].equals("/to"))) {
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
                                throw new MissingParameterException("Please provide an event in the format: event <task> /from <dd-MM-yyyy HHmm> /to <dd-MM-yyyy HHmm>");
                            }
                            try {
                                LocalDateTime startDate = Event.parseDate(start);
                                LocalDateTime endDate = Event.parseDate(end);
                                curr = new Event(task, false, startDate, endDate);
                                record.add(curr);
                                ui.printTaskAdded(curr, record.size());
                            } catch (DateTimeParseException e) {
                                throw new MissingParameterException("Please provide an event in the format: event <task> /from <dd-MM-yyyy HHmm> /to <dd-MM-yyyy HHmm> (e.g., 2019-10-15 1400)");
                            }
                        } else {
                            throw new InvalidCommandException("I don't recognize that command. Please use: todo, deadline, event, list, find, mark, unmark, or bye.");
                        }
                        s.store(record.getAll());
                    }
                } catch (FishballException e) {
                    ui.printError(e.getMessage());
                }
            }
        }
    }
}
