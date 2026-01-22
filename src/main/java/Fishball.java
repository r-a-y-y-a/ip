import java.util.Scanner;
import java.util.ArrayList;
public class Duke {
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
        ArrayList<String> record = new ArrayList<>();

        System.out.println(indent + horiline + indent + "Hello, I'm Fishball!\n");
        System.out.println(indent + "What can I do for you?\n" + indent + horiline);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                System.out.println(indent + horiline +
                                    indent + exitmsg +
                                    indent + horiline);
                return;
            }
            if (input.equals("list")){
                System.out.println(indent + horiline);
                for (int i = 0; i < record.size(); i++){
                    System.out.println(
                            indent + (i+1) + '.' + record.get(i)
                    );
                }
                System.out.println(indent + horiline);
            } else {
                record.add(input);
                System.out.println(indent + horiline +
                                    indent + "added: " + input + '\n' +
                                    indent + horiline);
            }
        }
    }
}
