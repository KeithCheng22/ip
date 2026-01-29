import java.io.File;
import java.util.List;

public class ListCommand extends Command {
    @Override
    public void execute(List<Task> tasks, Ui ui, Storage storage) {
        ui.drawHorizontalLine();
        ui.showMessage("Keef: ");
        ui.printTasks(tasks);
    }
}
