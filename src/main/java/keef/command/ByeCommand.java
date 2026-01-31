package keef.command;

import keef.storage.Storage;
import keef.task.TaskList;
import keef.ui.Ui;

/**
 * Represents a command that exits the application.
 * When executed, it displays a goodbye message to the user.
 */
public class ByeCommand extends Command {

    /**
     * Executes the bye command by showing a goodbye message.
     *
     * @param tasks   the task list (unused for this command)
     * @param ui      the user interface used to display the goodbye message
     * @param storage the storage component (unused for this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return true since this command does terminate the program
     */
    @Override
    public boolean isExit() {
        return true; // this command exits the program
    }
}
