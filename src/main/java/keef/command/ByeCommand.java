package keef.command;

import keef.storage.Storage;
import keef.task.TaskList;
import keef.ui.Ui;

public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true; // this command exits the program
    }
}
