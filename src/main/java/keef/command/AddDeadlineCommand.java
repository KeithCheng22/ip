package keef.command;

import java.time.LocalDateTime;

import keef.exception.KeefException;
import keef.storage.Storage;
import keef.task.Deadline;
import keef.task.Task;
import keef.task.TaskList;
import keef.ui.Ui;

/**
 * Represents a command that adds a deadline task to the task list.
 * A deadline consists of a description and a date/time specified using
 * the {@code /by} keyword.
 */
public class AddDeadlineCommand extends Command {
    private final String arguments;

    /**
     * Constructs an AddDeadlineCommand with the given arguments.
     *
     * @param arguments the raw arguments containing the task description
     *                  and deadline date/time
     */
    public AddDeadlineCommand(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes the command by parsing the arguments, creating a deadline task,
     * adding it to the task list, and saving it to storage.
     *
     * @param tasks   the task list to add the deadline to
     * @param ui      the user interface for displaying messages
     * @param storage the storage used to persist tasks
     * @throws KeefException if the input format is invalid or parsing fails
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        if (!arguments.contains("/by")) {
            throw new KeefException("Bro, you must include /by <date>");
        }

        String[] parts = arguments.split("/by", 2);
        String description = parts[0].trim();
        String dateString = parts[1].trim();

        if (description.isEmpty() || dateString.isEmpty()) {
            throw new KeefException("Bro, the description and date can't be empty!");
        }

        LocalDateTime by;
        try {
            by = LocalDateTime.parse(dateString, Task.STORAGE_FORMAT);
        } catch (Exception e) {
            throw new KeefException("Use date format: d/M/yyyy HHmm");
        }

        Task deadlineTask = new Deadline(description, by);

        int before = tasks.getSize();
        tasks.addTask(deadlineTask);
        assert deadlineTask != null : "Deadline Task should be created successfully";
        assert tasks.getSize() == before + 1 : "Task list size should increase by 1";

        storage.saveTasks(tasks);
        return ui.printMessage(deadlineTask, tasks.getSize(), CommandType.ADD);
    }
}
