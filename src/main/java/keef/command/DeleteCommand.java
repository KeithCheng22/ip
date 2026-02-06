package keef.command;

import keef.exception.KeefException;
import keef.parser.Parser;
import keef.storage.Storage;
import keef.task.Task;
import keef.task.TaskList;
import keef.ui.Ui;

/**
 * Represents a command that deletes a task from the task list
 * based on a user-specified task index.
 */
public class DeleteCommand extends Command {
    private final String arguments;

    /**
     * Constructs a {@code DeleteCommand} with the given arguments.
     *
     * @param arguments the task index provided by the user
     */
    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Deletes the specified task from the task list, saves the updated list,
     * and displays a confirmation message to the user.
     *
     * @param tasks the task list containing all current tasks
     * @param ui the user interface for displaying messages
     * @param storage the storage handler for persisting tasks
     * @throws KeefException if the task index is invalid or out of bounds
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        // Get the task to delete via the TaskIndex
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.getSize());
        Task task = tasks.getTask(taskIndex - 1);

        // Delete the task
        tasks.deleteTask(task);

        // Save and show message
        storage.saveTasks(tasks);
        return ui.printMessage(task, tasks.getSize(), CommandType.DELETE);
    }
}
