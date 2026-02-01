package utils;
import exceptions.EmptyTaskException;
import exceptions.FishballException;
import exceptions.InvalidCommandException;
import exceptions.InvalidIndexException;
import exceptions.MissingParameterException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo;


public class Storage {
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
