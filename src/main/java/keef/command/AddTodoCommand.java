package keef.command;

import keef.exception.KeefException;
import keef.storage.Storage;
import keef.task.Task;
import keef.task.TaskList;
import keef.task.ToDo;
import keef.ui.Ui;

/**
 * Represents a command that adds a todo task to the task list.
 * A todo task contains only a description and no date or time information.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Constructs an AddTodoCommand with the given task description.
     *
     * @param description the description of the todo task
     */
    public AddTodoCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the command by creating a todo task, adding it to the task list,
     * saving the updated list to storage, and displaying a confirmation message.
     *
     * @param tasks   the task list to add the todo task to
     * @param ui      the user interface for displaying messages
     * @param storage the storage used to persist tasks
     * @throws KeefException if the task description is empty
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        if (description.isEmpty()) {
            throw new KeefException("Bro, you left out what exactly you wanted to do! "
                    + "Add something!");
        }

        // Create the todo task
        Task task = new ToDo(description);

        int before = tasks.getSize();
        tasks.addTask(task);
        assert task != null : "ToDo Task should be created successfully";
        assert tasks.getSize() == before + 1 : "Task list size should increase by 1";

        // Save and show message
        storage.saveTasks(tasks);
        return ui.printMessage(task, tasks.getSize(), CommandType.ADD);
    }
}
