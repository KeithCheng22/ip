import java.io.File;
import java.util.List;

public class DeleteCommand extends Command {
    private final String arguments;

    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(List<Task> tasks, Ui ui, File dataFile) throws KeefException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.size());
        Task task = tasks.get(taskIndex - 1);

        tasks.remove(task);
        Keef.saveTasks(tasks, dataFile);
        ui.drawHorizontalLine();
        ui.showMessage("Keef: ");
        ui.printMessage(task, tasks.size(), CommandType.DELETE);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
