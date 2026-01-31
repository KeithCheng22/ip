package keef.command;

import keef.storage.Storage;
import keef.task.TaskList;
import keef.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.printTasks(tasks);
    }
}
