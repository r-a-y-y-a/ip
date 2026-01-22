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
            if (command.equals("bye") && parse.length == 1) {
                System.out.println(indent + horiline +
                                    indent + exitmsg +
                                    indent + horiline);
                return;
            }
            if (command.equals("list") && parse.length == 1){
                System.out.println(indent + horiline);
                System.out.println(indent + "Here are the tasks in your list:\n");
                for (int i = 0; i < record.size(); i++){
                    System.out.print(indent + (i+1) + '.');
                    if (record.get(i).checkDone()){
                        System.out.print("[X] ");
                    } else {
                        System.out.print("[ ] ");
                    }
                    System.out.print(record.get(i).getTask() + '\n');
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
                            indent + "   [X] " + record.get(index).getTask() + '\n' + indent + horiline);
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
                            indent + "   [ ] " + record.get(index).getTask() + '\n' + indent + horiline);
                    record.get(index).unmark();
                }

            } else {
                record.add(new Task(input, false));
                System.out.println(indent + horiline +
                                    indent + "added: " + input + '\n' +
                                    indent + horiline);
            }
        }
    }
}
