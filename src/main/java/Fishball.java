import exceptions.FishballException;

import utils.Storage;
import utils.TaskList;
import utils.UI;

public class Fishball {
    private String filepath;

    public Fishball(String filepath) {
        this.filepath = filepath;
    }

    public void run() throws FishballException {
        Storage storage = new Storage(filepath);
        TaskList record = new TaskList(storage.load());
        UI ui = new UI();

        ui.printWelcome();
        ui.handleInput(record, storage);
    }

    public static void main(String[] args) throws FishballException {
        Fishball fishball = new Fishball("../../../data/fishball.txt");
        fishball.run();
    }
}
