package keef;

import javafx.stage.Stage;
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

    public String getResponse(String input, Stage stage) {
        try {
            Command command = Parser.parse(input, stage);
            return command.execute(tasks, ui, storage);
        } catch (KeefException e) {
            return e.getMessage();
        }
    }
}
