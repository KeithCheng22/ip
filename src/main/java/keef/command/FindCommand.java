package keef.command;

import keef.exception.KeefException;
import keef.storage.Storage;
import keef.task.Task;
import keef.task.TaskList;
import keef.ui.Ui;

/**
 * Represents a command that finds and displays tasks whose descriptions
 * contain a given keyword.
 * <p>
 * The search is case-insensitive and matches any task whose description
 * contains the specified keyword as a substring.
 */
public class FindCommand extends Command {
    private final String arguments;

    /**
     * Constructs a {@code FindCommand} with the specified search keyword.
     *
     * @param arguments The keyword used to search task descriptions
     */
    public FindCommand(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes the find command by searching through the task list
     * and displaying all tasks whose descriptions contain the given keyword.
     *
     * @param tasks   The current list of tasks
     * @param ui      The UI component used to display messages to the user
     * @param storage The storage component (not modified by this command)
     * @throws KeefException If the search keyword is empty
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        if (arguments.isEmpty()) {
            throw new KeefException("Bro, you need to tell me what to find.");
        }

        TaskList matchingTasks = new TaskList();

        for (Task task : tasks.getAllTasks()) {
            if (task.getDescription().toLowerCase()
                    .contains(arguments.toLowerCase())) {
                matchingTasks.addTask(task);
            }
        }

        return ui.printFoundTasks(matchingTasks);
    }
}
