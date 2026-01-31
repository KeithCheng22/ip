package keef.command;

import keef.exception.KeefException;
import keef.parser.Parser;
import keef.storage.Storage;
import keef.task.Task;
import keef.task.TaskList;
import keef.ui.Ui;

public class UnmarkCommand extends Command {
    private final String arguments;

    public UnmarkCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.getSize());
        Task task = tasks.getTask(taskIndex - 1);

        if (!task.isDone()) {
            ui.drawHorizontalLine();
            ui.showMessage("keef.Keef: ");
            throw new KeefException("You didn't mark this task to begin with!");
        }

        tasks.unmarkTask(task);
        storage.saveTasks();
        ui.botReply();
        ui.printMessage(task, tasks.getSize(), CommandType.UNMARK);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
