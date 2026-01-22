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

class Task {
    String task;
    boolean done;
    public Task(String task, boolean done){
        this.task = task;
        this.done = done;
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
    private String deadline;
    public Deadline (String task, boolean done, String deadline){
        super(task, done);
        this.deadline = deadline;
    }
    @Override
    public String toString() {
        String out = "[D]";
        if (this.done){
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.task + "(by: " + deadline + ")";
        return out;
    }
}
class Todo extends Task {
    public Todo (String task, boolean done){
        super(task, done);
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
    private String start;
    private String end;
    public Event (String task, boolean done, String start, String end){
        super(task, done);
        this.start = start;
        this.end = end;
    }
    @Override
    public String toString() {
        String out = "[E]";
        if (this.done){
            out = out + "[X]";
        } else {
            out = out + "[ ]";
        }
        out = out + " " + this.task + "(from: " + this.start + " to: " + this.end + ")";
        return out;
    }
}
public class Fishball {
    public static String horiline = "____________________________________________________________\n";
    public static String exitmsg = "Bye. Hope to see you again soon!\n";
    public static String indent = "     ";
    public static void main(String[] args) throws FishballException{
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        //System.out.println(logo);
        ArrayList<Task> record = new ArrayList<>();
        ArrayList<Integer> done = new ArrayList<>();

        System.out.println(indent + horiline + indent + "Hello, I'm Fishball!\n");
        System.out.println(indent + "What can I do for you?\n" + indent + horiline);
        Scanner scanner = new Scanner(System.in);
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

                if (command.equals("delete")){
                    if (parse.length != 2){
                        throw new MissingParameterException("Please provide a delete in the format: delete <task number>");
                    } else {
                        try {
                            int index = Integer.parseInt(parse[1].trim()) - 1;
                            if (index < 0 || index >= record.size()){
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
                } else if (command.equals("list")){
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
                } else if (command.equals("mark") && parse.length == 2) {
                    int index = Integer.parseInt(parse[1]) - 1;
                    if (index < 0 || index >= record.size()){
                        throw new InvalidIndexException("Task number is out of range! Please provide a valid task number.");
                    }
                    record.get(index).mark();
                    System.out.println(indent + horiline +
                            indent + "Nice! I've marked this task as done\n" +
                            indent + record.get(index) + '\n' + indent + horiline);

                } else if (command.equals("unmark") && parse.length == 2) {
                    int index = Integer.parseInt(parse[1]) - 1;
                    if (index < 0 || index >= record.size()){
                        throw new InvalidIndexException("Task number is out of range! Please provide a valid task number.");
                    }
                    record.get(index).unmark();
                    System.out.println(indent + horiline +
                            indent + "Ok, I've marked this task as not done yet\n" +
                            indent + record.get(index) + '\n' + indent + horiline);
                } else {
                    String taskType = parse[0];
                    if (taskType.equals("todo")){
                        String task = "";
                        for (int i = 1; i < parse.length; i++){
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
                    } else if (taskType.equals("deadline")){
                        String task = "";
                        String deadline = "";
                        boolean hasBy = false;
                        for (int i = 1; i < parse.length; i++){
                            if (parse[i].equals("/by")){
                                hasBy = true;
                                for (int j = i+1; j < parse.length; j++){
                                    if (j == parse.length-1){
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
                            throw new MissingParameterException("Please provide a deadline in the format: deadline <task> /by <date>");
                        }
                        curr = new Deadline(task, false, deadline);
                        record.add(curr);
                        System.out.println(indent + horiline +
                                indent + "Got it. I've added this task: \n" +
                                indent + "    " + curr);
                    } else if (taskType.equals("event")){
                        String task = "";
                        String start = "";
                        String end = "";
                        boolean hasFrom = false;
                        boolean hasTo = false;
                        for (int i = 1; i < parse.length; i++){
                            if (parse[i].equals("/from")){
                                hasFrom = true;
                                i++;
                                while (i < parse.length && !parse[i].equals("/to")){
                                    if (i == parse.length - 1 || (i + 1 < parse.length && parse[i+1].equals("/to"))){
                                        start = start + parse[i];
                                        i++;
                                        break;
                                    }
                                    start = start + parse[i] + " ";
                                    i++;
                                }
                                i--;
                            }
                            if (parse[i].equals("/to")){
                                hasTo = true;
                                for (int j = i+1; j < parse.length; j++){
                                    if (j == parse.length - 1){
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
                            throw new MissingParameterException("Please provide a start time in the format: event <task> /from <start> /to <end>");
                        }
                        curr = new Event(task, false, start, end);
                        record.add(curr);
                        System.out.println(indent + horiline +
                                indent + "Got it. I've added this task: \n" +
                                indent + "    " + curr);
                    } else {
                        throw new InvalidCommandException("I don't recognize that command. Please use: todo, deadline, event, list, mark, unmark, or bye.");
                    }
                    System.out.println(indent + "Now you have " + record.size() + " tasks in the list.\n" + indent + horiline);
                }
            }catch (FishballException e) {
                System.out.println(indent + horiline + indent + "OOPS! Come on fishball! " + e.getMessage() + "\n" + indent + horiline);
            } catch (NumberFormatException e) {
                System.out.println(indent + horiline + indent + "OOPS! Please provide a valid task number.\n" + indent + horiline);
            }
        }
    }
}
