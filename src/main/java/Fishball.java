import java.util.Scanner;
import java.util.ArrayList;
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
    public static void main(String[] args) {
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
            String input = scanner.nextLine().trim();
            String[] parse = input.split(" ");
            String command = parse[0];
            Task curr;
            if (command.equals("bye") && parse.length == 1) {
                System.out.println(indent + horiline +
                                    indent + exitmsg +
                                    indent + horiline);
                return;
            }
            if (command.equals("list") && parse.length == 1){
                System.out.print(indent + horiline);
                System.out.println(indent + "Here are the tasks in your list:");
                for (int i = 0; i < record.size(); i++){
                    System.out.print(indent + (i+1) + '.');
                    System.out.println(record.get(i));
                }
                System.out.println(indent + horiline);
            } else if (command.equals("mark") && parse.length == 2) {
                int index = Integer.parseInt(parse[1]) - 1;
                if (index < 0 || index > record.size() - 1){
                    System.out.println(indent + horiline +
                            indent + "Please provide a valid task number!\n" +
                            indent + horiline);
                } else {
                    System.out.println(indent + horiline +
                            indent + "Nice! I've marked this task as done\n" +
                            indent + record.get(index) + '\n' + indent + horiline);
                    record.get(index).mark();
                }

            } else if (command.equals("unmark") && parse.length == 2) {
                int index = Integer.parseInt(parse[1]) - 1;
                if (index < 0 || index > record.size() - 1){
                    System.out.println(indent + horiline +
                            indent + "Please provide a valid task number!\n" +
                            indent + horiline);
                } else {
                    System.out.println(indent + horiline +
                            indent + "Nice! I've marked this task as not done yet\n" +
                            indent + record.get(index) + '\n' + indent + horiline);
                    record.get(index).unmark();
                }

            } else {
                String taskType = parse[0];
                if (taskType.equals("todo")){
                    String task = "";
                    for (int i = 1; i < parse.length; i++){
                        task = task + parse[i] + " ";
                    }
                    curr = new Todo(task, false);
                    record.add(curr);
                    System.out.println(indent + horiline +
                            indent + "Got it. I've added this task: \n" +
                            indent + "    " + curr);
                } else if (taskType.equals("deadline")){
                    String task = "";
                    String deadline = "";
                    for (int i = 1; i < parse.length; i++){
                        if (parse[i].equals("/by")){
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
                    curr = new Deadline(task, false, deadline);
                    record.add(curr);
                    System.out.println(indent + horiline +
                            indent + "Got it. I've added this task: \n" +
                            indent + "    " + curr);
                } else if (taskType.equals("event")){
                    String task = "";
                    String start = "";
                    String end = "";
                    for (int i = 1; i < parse.length; i++){
                        if (parse[i].equals("/from")){
                            i++;
                            while (!parse[i].equals("/to") && i < parse.length){
                                if (parse[i+1].equals("/to")){
                                    start = start + parse[i];
                                    i++;
                                    break;
                                }
                                start = start + parse[i] + " ";
                                i++;
                            }
                        }
                        if (parse[i].equals("/to")){
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
                    curr = new Event(task, false, start, end);
                    record.add(curr);
                    System.out.println(indent + horiline +
                            indent + "Got it. I've added this task: \n" +
                            indent + "    " + curr);
                } else {
                    System.out.println(indent + horiline +
                                       indent + "Please use a valid event type: task, deadline, event!\n" +
                                        indent + horiline);
                }
                System.out.println(indent + "Now you have " + record.size() + " tasks in the list.\n" + indent + horiline);
            }
        }
    }
}
