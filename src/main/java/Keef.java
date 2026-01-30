public class Keef {
    private Storage storage;
    private Ui ui;
    private TaskList tasks;

    public Keef(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = storage.loadTasks();
        } catch (Exception e) {
            tasks = new TaskList();
        }
    }

    public void run(){
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (KeefException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.drawHorizontalLine();
            }
        }
    }

    public static void main(String[] args) {
        new Keef("./data/keef.txt").run();
    }
}
