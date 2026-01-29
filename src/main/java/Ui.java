import java.util.List;
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
        System.out.print("Keef: ");
        System.out.println("See ya soon! ~");
        drawHorizontalLine();
    }

    public void showMessage(String message) {
        System.out.print(message);
    }

    public void printTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("You don't have any tasks yet. Start adding!");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + "." + " " + tasks.get(i));
            }
        }
        drawHorizontalLine();
    }

    public void printMessage(Task task, int size, CommandType type) {
        String pastTenseType = switch (type) {
            case ADD -> "added";
            case DELETE -> "deleted";
            case MARK -> "marked";
            case UNMARK -> "unmarked";
            default -> "";
        };
        System.out.println("Got it. I've " + pastTenseType + " this task:");
        System.out.println(task);
        System.out.println("Now you have " + size + " tasks in your list.");
        drawHorizontalLine();
    }

    public void drawHorizontalLine() {
        int length = 50;
        System.out.println("-".repeat(length));
    }


}
