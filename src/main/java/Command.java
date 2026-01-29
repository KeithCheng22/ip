import java.io.File;
import java.util.List;

public abstract class Command {
    public abstract void execute(List<Task> tasks, Ui ui, File dataFile) throws KeefException;
    public boolean isExit() {
        return false;
    }
}
