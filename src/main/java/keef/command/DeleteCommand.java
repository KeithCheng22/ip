package keef.command;

import keef.exception.KeefException;
import keef.parser.Parser;
import keef.storage.Storage;
import keef.task.Task;
import keef.task.TaskList;
import keef.ui.Ui;

public class DeleteCommand extends Command {
    private final String arguments;

    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        // Get the task to delete via the TaskIndex
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.getSize());
        Task task = tasks.getTask(taskIndex - 1);

        // Delete the task
        tasks.deleteTask(task);

        // Save and show message
        storage.saveTasks();
        ui.printMessage(task, tasks.getSize(), CommandType.DELETE);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
