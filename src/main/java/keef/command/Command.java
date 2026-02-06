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
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws KeefException;
}
