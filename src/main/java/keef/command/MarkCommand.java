package keef.command;

import keef.exception.KeefException;
import keef.parser.Parser;
import keef.storage.Storage;
import keef.task.Task;
import keef.task.TaskList;
import keef.ui.Ui;

public class MarkCommand extends Command {
    private final String arguments;

    public MarkCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        // Get the task to mark via the TaskIndex
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.getSize());
        Task task = tasks.getTask(taskIndex - 1);

        // Throw exception if task is already marked as done
        if (task.isDone()) {
            throw new KeefException("You are already done with this task!");
        }

        // Mark the task
        tasks.markTask(task);

        // Save and show message
        storage.saveTasks();
        ui.printMessage(task, tasks.getSize(), CommandType.MARK);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
