import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.File;

public class AddEventCommand extends Command {
    private final String arguments;
    private Task eventTask;

    public AddEventCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
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

    @Override
    public boolean isExit() {
        return false;
    }
}
