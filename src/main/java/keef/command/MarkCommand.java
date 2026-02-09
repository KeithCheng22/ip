package keef.command;

import java.util.List;

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
    public String execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        List<Integer> taskIndices = Parser.parseTaskIndices(arguments, tasks.getSize());

        StringBuilder markedMsg = new StringBuilder("Got it! I've marked:\n");
        StringBuilder skippedMsg = new StringBuilder();

        int markedCount = 0;

        for (Integer taskIndex : taskIndices) {
            Task task = tasks.getTask(taskIndex - 1);

            if (!task.isDone()) {
                task.markAsDone();
                markedMsg.append(task).append("\n");
                markedCount++;
            } else {
                skippedMsg.append(task).append("\n");
            }
        }

        if (markedCount == 0) {
            throw new KeefException("All selected tasks are already done!");
        }

        storage.saveTasks(tasks);

        if (!skippedMsg.isEmpty()) {
            markedMsg.append("\nNote: The following tasks were already done and skipped:\n")
                    .append(skippedMsg);
        }

        return markedMsg.toString();
    }
}
