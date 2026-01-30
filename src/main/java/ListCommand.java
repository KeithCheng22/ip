public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.botReply();
        ui.printTasks(tasks);
    }
}
