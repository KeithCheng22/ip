package keef.command;

import java.util.Comparator;
import java.util.List;

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
        List<Integer> taskIndices = Parser.parseTaskIndices(arguments, tasks.getSize());

        // Delete in reverse order to avoid index shifting
        taskIndices.sort(Comparator.reverseOrder());

        StringBuilder message = new StringBuilder("Got it! Deleted tasks:\n");

        for (Integer taskIndex : taskIndices) {
            Task task = tasks.getTask(taskIndex - 1);
            tasks.deleteTask(task);
            message.append(task).append("\n");
        }

        storage.saveTasks(tasks);
        return message.toString();
    }
}
