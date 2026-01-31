package keef.command;

import keef.exception.KeefException;
import keef.parser.Parser;
import keef.storage.Storage;
import keef.task.Task;
import keef.task.TaskList;
import keef.ui.Ui;

/**
 * Represents a command to unmark a task as not done.
 * The task to be unmarked is specified by its index in the task list.
 */
public class UnmarkCommand extends Command {
    private final String arguments;

    /**
     * Constructs an UnmarkCommand with the specified task index as a string.
     *
     * @param arguments the string representing the task index to unmark
     */
    public UnmarkCommand(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes the unmark command.
     * Marks the specified task as not done, saves the updated task list to storage,
     * and prints confirmation via the UI.
     *
     * @param tasks the task list containing all current tasks
     * @param ui the user interface for displaying messages
     * @param storage the storage handler for saving changes
     * @throws KeefException if the task index is invalid or the task is already unmarked
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.getSize());
        Task task = tasks.getTask(taskIndex - 1);

        if (!task.isDone()) {
            ui.drawHorizontalLine();
            ui.showMessage("keef.Keef: ");
            throw new KeefException("You didn't mark this task to begin with!");
        }

        tasks.unmarkTask(task);
        storage.saveTasks();
        ui.botReply();
        ui.printMessage(task, tasks.getSize(), CommandType.UNMARK);
    }

    /**
     * Indicates whether this command exits the application.
     *
     * @return false since this command does not terminate the program
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
