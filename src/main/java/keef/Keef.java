package keef;

import keef.command.Command;
import keef.exception.KeefException;
import keef.parser.Parser;
import keef.storage.Storage;
import keef.task.TaskList;
import keef.ui.Ui;

/**
 * The main class for the Keef application.
 *
 * This class is responsible for initializing the storage, user interface,
 * and task list, and for running the main interaction loop with the user.
 */
public class Keef {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Constructs a new Keef instance with the given file path for storage.
     *
     * @param filePath the path to the file where tasks are stored
     */
    public Keef(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = storage.loadTasks();
        } catch (Exception e) {
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main interaction loop of the application.
     *
     * Continuously reads user commands, executes them, and displays responses
     * until the user exits the application.
     */
    public void run(){
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command command = Parser.parse(fullCommand);
                ui.botReply();
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (KeefException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.drawHorizontalLine();
            }
        }
    }

    /**
     * The entry point of the Keef application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Keef("./data/keef.txt").run();
    }
}
