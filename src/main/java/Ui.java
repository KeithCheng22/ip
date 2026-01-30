import java.util.Scanner;

public class Ui {
    private final Scanner SCANNER;

    public Ui() {
        SCANNER = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.print("You: ");
        return SCANNER.nextLine();
    }

    public void showWelcome() {
        drawHorizontalLine();
        System.out.println("Hello! I'm Keef!\nWhat can I do for you? ~");
        drawHorizontalLine();
    }

    public void showGoodbye() {
        botReply();
        System.out.println("See ya soon! ~");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void printTasks(TaskList tasks) {
        if (tasks.getSize() == 0) {
            showMessage("You don't have any tasks yet. Start adding!");
        } else {
            showMessage("Here are the tasks in your list:");
            for (int i = 0; i < tasks.getSize(); i++) {
                showMessage(i + 1 + "." + " " + tasks.getTask(i));
            }
        }
    }

    public void botReply() {
        drawHorizontalLine();
        showMessage("Keef: ");
    }

    public void showError(String message) {
        botReply();
        showMessage(message);
    }

    public void printMessage(Task task, int size, CommandType type) {
        botReply();
        String pastTenseType = switch (type) {
            case ADD -> "added";
            case DELETE -> "deleted";
            case MARK -> "marked";
            case UNMARK -> "unmarked";
            default -> "";
        };
        showMessage("Got it. I've " + pastTenseType + " this task:");
        showMessage(task.toString());
        showMessage("Now you have " + size + " tasks in your list.");
    }

    public void drawHorizontalLine() {
        int length = 50;
        System.out.println("-".repeat(length));
    }
}
