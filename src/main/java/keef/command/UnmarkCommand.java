package keef.command;

import java.util.List;

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
    public String execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        List<Integer> taskIndices = Parser.parseTaskIndices(arguments, tasks.getSize());

        StringBuilder unmarkedMsg = new StringBuilder("Got it! I've unmarked:\n");
        StringBuilder skippedMsg = new StringBuilder();

        int unmarkedCount = 0;

        for (Integer taskIndex : taskIndices) {
            Task task = tasks.getTask(taskIndex - 1);

            if (task.isDone()) {
                task.markAsUndone();
                unmarkedMsg.append(task).append("\n");
                unmarkedCount++;
            } else {
                skippedMsg.append(task).append("\n");
            }
        }

        if (unmarkedCount == 0) {
            throw new KeefException("All selected tasks are already unmarked!");
        }

        storage.saveTasks(tasks);

        if (!skippedMsg.isEmpty()) {
            unmarkedMsg.append("\nNote: The following tasks were already unmarked and skipped:\n")
                    .append(skippedMsg);
        }

        return unmarkedMsg.toString();
    }
}
