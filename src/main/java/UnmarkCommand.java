import java.io.File;
import java.util.List;

public class UnmarkCommand extends Command {
    private final String arguments;

    public UnmarkCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(List<Task> tasks, Ui ui, Storage storage) throws KeefException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.size());
        Task task = tasks.get(taskIndex - 1);

        if (!task.isDone()) {
            ui.drawHorizontalLine();
            ui.showMessage("Keef: ");
            throw new KeefException("You didn't mark this task to begin with!");
        }

        task.markAsUndone();
        storage.saveTasks();
        ui.botReply();
        ui.printMessage(task, tasks.size(), CommandType.UNMARK);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
