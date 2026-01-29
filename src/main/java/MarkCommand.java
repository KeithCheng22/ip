import java.io.File;
import java.util.List;

public class MarkCommand extends Command {
    private final String arguments;

    public MarkCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(List<Task> tasks, Ui ui, Storage storage) throws KeefException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.size());
        Task task = tasks.get(taskIndex - 1);

        if (task.isDone()) {
            ui.drawHorizontalLine();
            ui.showMessage("Keef: ");
            throw new KeefException("You are already done with this task!");
        }

        task.markAsDone();
        storage.saveTasks();
        ui.drawHorizontalLine();
        ui.showMessage("Keef: ");
        ui.printMessage(task, tasks.size(), CommandType.MARK);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
