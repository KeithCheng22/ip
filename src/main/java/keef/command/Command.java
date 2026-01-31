package keef.command;

import keef.exception.KeefException;
import keef.storage.Storage;
import keef.task.TaskList;
import keef.ui.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException;
    public boolean isExit() {
        return false;
    }
}
