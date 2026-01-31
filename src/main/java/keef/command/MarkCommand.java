package keef.command;

import keef.exception.KeefException;
import keef.parser.Parser;
import keef.storage.Storage;
import keef.task.Task;
import keef.task.TaskList;
import keef.ui.Ui;

/**
 * Represents a command to mark a task as done.
 * The task to be marked is specified by its index in the task list.
 */
public class MarkCommand extends Command {
    private final String arguments;

    /**
     * Constructs a MarkCommand with the specified task index as a string.
     *
     * @param arguments the string representing the task index to mark as done
     */
    public MarkCommand(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes the mark command.
     * Marks the specified task as done, saves the updated task list to storage,
     * and prints confirmation via the UI.
     *
     * @param tasks the task list containing all current tasks
     * @param ui the user interface for displaying messages
     * @param storage the storage handler for saving changes
     * @throws KeefException if the task index is invalid or the task is already done
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.getSize());
        Task task = tasks.getTask(taskIndex - 1);

        if (task.isDone()) {
            throw new KeefException("You are already done with this task!");
        }

        tasks.markTask(task);
        storage.saveTasks();
        ui.printMessage(task, tasks.getSize(), CommandType.MARK);
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
