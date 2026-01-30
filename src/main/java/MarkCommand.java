public class MarkCommand extends Command {
    private final String arguments;

    public MarkCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.getSize());
        Task task = tasks.getTask(taskIndex - 1);

        if (task.isDone()) {
            throw new KeefException("You are already done with this task!");
        }

        tasks.markTask(task);
        storage.saveTasks();
        ui.printMessage(task, tasks.getSize(), CommandType.MARK);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
