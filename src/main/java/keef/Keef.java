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
    private final TaskList tasks;

    /**
     * Constructs a new Keef instance with the given file path for storage.
     *
     * @param filePath the path to the file where tasks are stored
     */
    public Keef(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = loadTasksSafely();
    }

    /**
     * Processes user input and returns Keef's response.
     *
     * @param input the user input string
     * @param stage the JavaFX stage for GUI operations (used by some commands)
     * @return the response string from executing the corresponding command
     */
    public String getResponse(String input, Stage stage) {
        try {
            Command command = Parser.parse(input, stage);
            return command.execute(tasks, ui, storage);
        } catch (KeefException e) {
            return e.getMessage();
        }
    }

    /**
     * Loads tasks from storage safely, falling back to an empty task list
     * if any exception occurs.
     *
     * @return a TaskList loaded from storage or empty if loading fails
     */
    private TaskList loadTasksSafely() {
        try {
            return storage.loadTasks();
        } catch (Exception e) {
            return new TaskList();
        }
    }
}
