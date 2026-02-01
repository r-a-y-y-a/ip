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

import utils.Storage;
import utils.TaskList;
import utils.UI;

public class Fishball {
    private String filepath;

    public Fishball(String filepath) {
        this.filepath = filepath;
    }

    public void run() throws FishballException {
        Storage s = new Storage(filepath);
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
                    }
                    s.store(record.getAll());
                } catch (FishballException e) {
                    ui.printError(e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) throws FishballException {
        Fishball fishball = new Fishball("../../../data/fishball.txt");
        fishball.run();
    }
}
