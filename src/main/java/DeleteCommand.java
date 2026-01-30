public class DeleteCommand extends Command {
    private final String arguments;

    public DeleteCommand(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws KeefException {
        int taskIndex = Parser.parseTaskIndex(arguments, tasks.getSize());
        Task task = tasks.getTask(taskIndex - 1);

        tasks.deleteTask(task);
        storage.saveTasks();
        ui.botReply();
        ui.printMessage(task, tasks.getSize(), CommandType.DELETE);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
