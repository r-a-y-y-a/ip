import exceptions.FishballException;
import utils.Storage;
import utils.TaskList;
import utils.UI;

/**
 * Fishball is the main application class that orchestrates the task management system.
 * It initializes the storage, task list, and user interface, then delegates input handling
 * to the UI.
 *
 * @author r-a-y-y-a
 * @version 1.0
 */
public class Fishball {
    private String filepath;

    /**
     * Constructs a Fishball instance with the specified file path for data persistence.
     *
     * @param filepath the path to the file where tasks will be stored and loaded
     */
    public Fishball(String filepath) {
        this.filepath = filepath;
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
