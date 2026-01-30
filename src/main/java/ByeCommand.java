import java.io.File;
import java.util.List;

public class ByeCommand extends Command {
    @Override
    public void execute(List<Task> tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true; // this command exits the program
    }
}
