import java.io.File;
import java.util.List;

public class AddTodoCommand extends Command {
    private final String description;

    public AddTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public void execute(List<Task> tasks, Ui ui, File dataFile) throws KeefException {
        if (description.isEmpty()) throw new KeefException("Bro, you left out what exactly you wanted to do! Add something!");

        Task task = new ToDo(description);
        tasks.add(task);
        Keef.saveTasks(tasks, dataFile);
        ui.drawHorizontalLine();
        ui.showMessage("Keef: ");
        ui.printMessage(task, tasks.size(), CommandType.ADD);
    }
}