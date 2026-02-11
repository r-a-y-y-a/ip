import exceptions.FishballException;
import utils.Storage;
import utils.TaskList;
import utils.UI;

/**
 * Fishball is the main application class that orchestrates the task management system.
 * It initializes the storage, task list, and user interface, then delegates input handling
 * to the UI. It can operate in two modes: CLI mode via the run() method or GUI mode where
 * the caller directly accesses the TaskList and Storage components.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class Fishball {
    private String filepath;
    private Storage storage;
    private TaskList taskList;
    private boolean isRunning;

    /**
     * Constructs a Fishball instance with the specified file path for data persistence.
     * Initializes Storage and loads the TaskList.
     *
     * @param filepath the path to the file where tasks will be stored and loaded
     */
    public Fishball(String filepath) {
        this.filepath = filepath;
        this.storage = new Storage(filepath);
        this.taskList = new TaskList(storage.load());
        this.isRunning = true;
    }

    /**
     * Runs the Fishball application.
     * Loads existing tasks from storage, displays a welcome message, and begins processing user input.
     *
     * @throws FishballException if an error occurs during execution
     */
    public void run() throws FishballException {
        Storage storage = new Storage(filepath);
        TaskList record = new TaskList(storage.load());
        UI ui = new UI();

        ui.printWelcome();
        ui.handleInput(record, storage);
    }


    /**
     * Retrieves the TaskList for direct access (used by GUI).
     *
     * @return the current TaskList
     */
    public TaskList getTaskList() {
        return taskList;
    }

    /**
     * Retrieves the Storage instance for direct access (used by GUI).
     *
     * @return the current Storage instance
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Processes a single user command and returns a response message.
     * This method delegates to the UI's processCommand method, which handles
     * all command parsing, execution, and storage updates.
     * If the command is "bye", sets isRunning to false.
     *
     * @param input the user's command input
     * @return a message describing the result of the command
     */
    public String getResponse(String input) {
        if (input.trim().equals("bye")) {
            isRunning = false;
        }
        UI ui = new UI();
        return ui.processCommand(input, taskList, storage);
    }

    /**
     * Checks if the application should continue running.
     * Returns false after the bye command is executed.
     *
     * @return true if the application should keep running, false if it should exit
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Entry point for the Fishball application.
     *
     * @param args command-line arguments (not used)
     * @throws FishballException if an error occurs during application startup or execution
     */
    public static void main(String[] args) throws FishballException {
        Fishball fishball = new Fishball("../../../data/fishball.txt");
        fishball.run();
    }
}
