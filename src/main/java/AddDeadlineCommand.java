import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddDeadlineCommand extends Command {
    private final String arguments;
    private Task deadlineTask;

    public AddDeadlineCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(List<Task> tasks, Ui ui, Storage storage) throws KeefException {
        // Validate arguments
        if (!arguments.contains("/by")) {
            throw new KeefException("Bro, you must include /by <date>");
        }

        String[] parts = arguments.split("/by", 2);
        String description = parts[0].trim();
        String dateString = parts[1].trim();

        if (description.isEmpty() || dateString.isEmpty()) {
            throw new KeefException("Bro, the description and date can't be empty!");
        }

        // Parse the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        LocalDateTime by;
        try {
            by = LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            throw new KeefException("Use date format: d/M/yyyy HHmm");
        }

        // Create the deadline task
        deadlineTask = new Deadline(description, by);
        tasks.add(deadlineTask);

        // Save and show message
        storage.saveTasks();
        ui.printMessage(deadlineTask, tasks.size(), CommandType.ADD);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
