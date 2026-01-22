import java.util.Scanner;

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
            System.out.println(indent + horiline +
                                indent + input + '\n' +
                                indent + horiline);
        }
    }
}
