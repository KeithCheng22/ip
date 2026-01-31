package keef.command;

import keef.exception.KeefException;
import keef.storage.Storage;
import keef.task.Event;
import keef.task.Task;
import keef.task.TaskList;
import keef.ui.Ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a command that adds an event task to the task list.
 * An event consists of a description, a start date/time, and an end date/time,
 * specified using the {@code /from} and {@code /to} keywords.
 */
public class AddEventCommand extends Command {
    private final String arguments;
    private Task eventTask;

    /**
     * Constructs an AddEventCommand with the given arguments.
     *
     * @param arguments the raw arguments containing the task description,
     *                  start date/time, and end date/time
     */
    public AddEventCommand(String arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes the command by parsing the arguments, creating an event task,
     * adding it to the task list, and saving it to storage.
     *
     * @param tasks   the task list to add the event to
     * @param ui      the user interface for displaying messages
     * @param storage the storage used to persist tasks
     * @throws KeefException if required fields are missing or date parsing fails
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        // Validate arguments
        if (!arguments.contains("/from") || !arguments.contains("/to")) {
            throw new KeefException("Bro, you must include /from <start> and /to <end>.");
        }

        String[] parts = arguments.split("/from|/to");
        if (parts.length < 3) {
            throw new KeefException("Bro, the event description, start, and end cannot be empty!");
        }

        String description = parts[0].trim();
        String dateFromString = parts[1].trim();
        String dateToString = parts[2].trim();

        if (description.isEmpty() || dateFromString.isEmpty() || dateToString.isEmpty()) {
            throw new KeefException("Bro, the event description, start, and end cannot be empty!");
        }

        // Parse the dates
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime from;
        LocalDateTime to;
        try {
            from = LocalDateTime.parse(dateFromString, formatter);
            to = LocalDateTime.parse(dateToString, formatter);
        } catch (Exception e) {
            throw new KeefException("Use date format: d/M/yyyy HHmm");
        }

        // Create the event task
        eventTask = new Event(description, from, to);
        tasks.addTask(eventTask);

        // Save and show message
        storage.saveTasks();
        ui.printMessage(eventTask, tasks.getSize(), CommandType.ADD);
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
