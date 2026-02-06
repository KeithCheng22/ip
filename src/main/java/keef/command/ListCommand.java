package keef.command;

import keef.storage.Storage;
import keef.task.TaskList;
import keef.ui.Ui;

/**
 * Represents a command that lists all tasks in the task list.
 * When executed, it prints all tasks to the user via the UI.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by printing all tasks in the provided task list.
     *
     * @param tasks the task list containing all current tasks
     * @param ui the user interface for displaying messages
     * @param storage the storage handler (not used in this command)
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.printTasks(tasks);
    }
}
