import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
// Custom Exception Classes
class FishballException extends Exception {
    public FishballException(String message) {
        super(message);
    }
}

class EmptyTaskException extends FishballException {
    public EmptyTaskException(String message) {
        super(message);
    }
}

class InvalidIndexException extends FishballException {
    public InvalidIndexException(String message) {
        super(message);
    }
}

class InvalidCommandException extends FishballException {
    public InvalidCommandException(String message) {
        super(message);
    }
}

class MissingParameterException extends FishballException {
    public MissingParameterException(String message) {
        super(message);
    }
}

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
class Task {
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
class Deadline extends Task {
    private final LocalDateTime deadline;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    public Deadline (String task, boolean done, LocalDateTime deadline){
        super("d", task, done);
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    public static LocalDateTime parseDate(String dateStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateStr, INPUT_FORMATTER);
    }

    @Override
    public String toString() {
        String out = "[D]";
        if (this.done){
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.task + "(by: " + deadline.format(OUTPUT_FORMATTER) + ")";
        return out;
    }
}
class Todo extends Task {
    public Todo (String task, boolean done){
        super("t", task, done);
    }
    @Override
    public String toString() {
        String out = "[T]";
        if (this.done){
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.task;
        return out;
    }
}

class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    public Event (String task, boolean done, LocalDateTime start, LocalDateTime end){
        super("e", task, done);
        this.start = start;
        this.end = end;
    }

    public LocalDateTime getStart(){
        return this.start;
    }

    public LocalDateTime getEnd(){
        return this.end;
    }

    public static LocalDateTime parseDate(String dateStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateStr, INPUT_FORMATTER);
    }

    @Override
    public String toString() {
        String out = "[E]";
        if (this.done){
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.task + "(from: " + this.start.format(OUTPUT_FORMATTER) + " to: " + this.end.format(OUTPUT_FORMATTER) + ")";
        return out;
    }
}

//public String[] getData()
public class Fishball {
    public static String horiline = "____________________________________________________________\n";
    public static String exitmsg = "Bye. Hope to see you again soon!\n";
    public static String indent = "     ";
    public static void main(String[] args) throws FishballException{
        //System.out.println(logo);
        Storage s = new Storage("../../../data/fishball.txt");
        ArrayList<Task> record = s.load();

        System.out.println(indent + horiline + indent + "Hello, I'm Fishball!\n");
        System.out.println(indent + "What can I do for you?\n" + indent + horiline);
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
                        System.out.println(indent + horiline +
                                indent + exitmsg +
                                indent + horiline);
                        return;
                    }

                    if (command.equals("list")){
                        if (parse.length != 1){
                            throw new InvalidCommandException("The list command does not take any parameters! Just type 'list' to see your tasks.");
                        }
                        System.out.print(indent + horiline);
                        System.out.println(indent + "Here are the tasks in your list:");
                        for (int i = 0; i < record.size(); i++){
                            System.out.print(indent + (i+1) + '.');
                            System.out.println(record.get(i));
                        }
                        System.out.println(indent + horiline);
                    } else if (command.equals("find")){
                        if (parse.length != 3){
                            throw new InvalidCommandException("The find command requires a date and time! Use format: find <dd-MM-yyyy> <HHmm>");
                        }
                        try {
                            LocalDateTime searchDateTime = LocalDateTime.parse(parse[1] + " " + parse[2], DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
                            System.out.print(indent + horiline);
                            System.out.println(indent + "Tasks on " + searchDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HHmm")) + ":");
                            boolean found = false;
                            for (int i = 0; i < record.size(); i++){
                                Task t = record.get(i);
                                boolean matches = false;
                                if (t.getType().equals("d")){
                                    Deadline d = (Deadline) t;
                                    if (d.getDeadline().equals(searchDateTime)) matches = true;
                                } else if (t.getType().equals("e")) {
                                    Event e = (Event) t;
                                    if ((e.getStart().equals(searchDateTime) || e.getEnd().equals(searchDateTime)) ||
                                            (e.getStart().isBefore(searchDateTime) && e.getEnd().isAfter(searchDateTime))) {
                                        matches = true;
                                    }
                                }
                                if (matches) {
                                    System.out.print(indent + (i+1) + '.');
                                    System.out.println(record.get(i));
                                    found = true;
                                }
                            }
                            if (!found) {
                                System.out.println(indent + "No tasks found for this date/time.");
                            }
                            System.out.println(indent + horiline);
                        } catch (DateTimeParseException e) {
                            throw new InvalidCommandException("Invalid date/time format! Use: find <dd-MM-yyyy> <HHmm>");
                        }
                    } else {
                        if (command.equals("delete")) {
                            if (parse.length != 2) {
                                throw new MissingParameterException("Please provide a delete in the format: delete <task number>");
                            } else {
                                try {
                                    int index = Integer.parseInt(parse[1].trim()) - 1;
                                    if (index < 0 || index >= record.size()) {
                                        throw new InvalidIndexException("Task number is out of range! Please provide a valid task number.");
                                    }
                                    Task delete = record.get(index);
                                    record.remove(index);
                                    System.out.println(indent + horiline + indent + "Noted. I've removed this task:\n" + indent + "  " + delete);
                                    System.out.println(indent + "Now you have " + record.size() + " tasks in the list.\n" + indent + horiline);
                                } catch (NumberFormatException e) {
                                    throw new MissingParameterException("Please provide a delete in the format: delete <task number>");
                                }
                            }
                        } else if (command.equals("mark") && parse.length == 2) {
                            int index = Integer.parseInt(parse[1]) - 1;
                            if (index < 0 || index >= record.size()) {
                                throw new InvalidIndexException("Task number is out of range! Please provide a valid task number.");
                            }
                            record.get(index).mark();
                            System.out.println(indent + horiline +
                                    indent + "Nice! I've marked this task as done\n" +
                                    indent + record.get(index) + '\n' + indent + horiline);

                        } else if (command.equals("unmark") && parse.length == 2) {
                            int index = Integer.parseInt(parse[1]) - 1;
                            if (index < 0 || index >= record.size()) {
                                throw new InvalidIndexException("Task number is out of range! Please provide a valid task number.");
                            }
                            record.get(index).unmark();
                            System.out.println(indent + horiline +
                                    indent + "Ok, I've marked this task as not done yet\n" +
                                    indent + record.get(index) + '\n' + indent + horiline);
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
                                System.out.println(indent + horiline +
                                        indent + "Got it. I've added this task: \n" +
                                        indent + "    " + curr);
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
                                    System.out.println(indent + horiline +
                                            indent + "Got it. I've added this task: \n" +
                                            indent + "    " + curr);
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
                                        i--;
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
                                    System.out.println(indent + horiline +
                                            indent + "Got it. I've added this task: \n" +
                                            indent + "    " + curr);
                                } catch (DateTimeParseException e) {
                                    throw new MissingParameterException("Please provide an event in the format: event <task> /from <dd-MM-yyyy HHmm> /to <dd-MM-yyyy HHmm> (e.g., 2019-10-15 1400)");
                                }
                            } else {
                                throw new InvalidCommandException("I don't recognize that command. Please use: todo, deadline, event, list, find, mark, unmark, or bye.");
                            }
                            System.out.println(indent + "Now you have " + record.size() + " tasks in the list.\n" + indent + horiline);
                        }
                        s.store(record);


                    }
                } catch (FishballException e) {
                    System.out.println(indent + horiline + indent + "OOPS! Come on fishball! " + e.getMessage() + "\n" + indent + horiline);
                } catch (NumberFormatException e) {
                    System.out.println(indent + horiline + indent + "OOPS! Please provide a valid task number.\n" + indent + horiline);
                }
            }
        }
    }
}
