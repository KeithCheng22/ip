package keef.command;

import javafx.stage.Stage;
import keef.storage.Storage;
import keef.task.TaskList;
import keef.ui.Ui;

/**
 * Represents a command that exits the application.
 * When executed, it displays a goodbye message to the user.
 */
public class ByeCommand extends Command {
    private final Stage stage;

    public ByeCommand(Stage stage) {
        this.stage = stage;
    }

    /**
     * Executes the bye command by showing a goodbye message.
     *
     * @param tasks   the task list (unused for this command)
     * @param ui      the user interface used to display the goodbye message
     * @param storage the storage component (unused for this command)
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        if (stage != null) {
            stage.close();
        }

        return "";
    }
}
