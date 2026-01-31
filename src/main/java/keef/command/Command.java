package keef.command;

import keef.exception.KeefException;
import keef.storage.Storage;
import keef.task.TaskList;
import keef.ui.Ui;

/**
 * Represents an executable user command in the Keef application.
 * Each concrete command defines its own execution behavior.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @param tasks   the task list to operate on
     * @param ui      the user interface for displaying output
     * @param storage the storage component for saving data
     * @throws KeefException if an error occurs during command execution
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException;

    /**
     * Indicates whether this command exits the application.
     *
     * @return false since this command does not terminate the program
     */
    public boolean isExit() {
        return false;
    }
}
