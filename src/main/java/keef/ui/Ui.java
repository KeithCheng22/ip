package keef.ui;

import java.util.Scanner;

import keef.command.CommandType;
import keef.task.Task;
import keef.task.TaskList;

/**
 * Ui handles all interactions with the user via the console.
 * It is responsible for reading user input and printing messages,
 * including task lists, errors, and system responses.
 */
public class Ui {
    private final Scanner sc;

    /**
     * Constructs an Ui object and initializes the input scanner.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Reads a line of input from the user.
     *
     * @return the full command typed by the user
     */
    public String readCommand() {
        System.out.print("You: ");
        return sc.nextLine();
    }

    /**
     * Prints the welcome message to the user.
     */
    public void showWelcome() {
        drawHorizontalLine();
        System.out.println("Hello! I'm Keef!\nWhat can I do for you? ~");
        drawHorizontalLine();
    }

    /**
     * Prints a goodbye message when the program exits.
     */
    public void showGoodbye() {
        System.out.println("See ya soon! ~");
    }

    /**
     * Prints a custom message to the console.
     *
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints all tasks in a TaskList with their numbering.
     *
     * @param tasks the TaskList to display
     */
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

    /**
     * Prints a line indicating that Keef is replying,
     * followed by a horizontal line.
     */
    public void botReply() {
        drawHorizontalLine();
        showMessage("Keef: ");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to show
     */
    public void showError(String message) {
        showMessage(message);
    }

    /**
     * Prints a confirmation message for task actions such as add, delete, mark, or unmark.
     *
     * @param task the task affected
     * @param size the current size of the task list
     * @param type the type of command performed
     */
    public void printMessage(Task task, int size, CommandType type) {
        //CHECKSTYLE.OFF: Indentation
        String pastTenseType = switch (type) {
            case ADD -> "added";
            case DELETE -> "deleted";
            case MARK -> "marked";
            case UNMARK -> "unmarked";
            default -> "";
        };
        //CHECKSTYLE.ON: Indentation
        showMessage("Got it. I've " + pastTenseType + " this task:");
        showMessage(task.toString());
        showMessage("Now you have " + size + " tasks in your list.");
    }

    /**
     * Displays all tasks that match a search query.
     * <p>
     * If no matching tasks are found, a message is shown to inform the user.
     * Otherwise, each matching task is printed with an index, followed by
     * the total number of tasks found.
     *
     * @param tasks the {@code TaskList} containing tasks that match the search keyword
     */
    public void printFoundTasks(TaskList tasks) {
        if (tasks.getSize() == 0) {
            showMessage("No matching tasks found.");
        } else {
            showMessage("Here are the matching tasks in your list:");
            for (int i = 0; i < tasks.getSize(); i++) {
                showMessage((i + 1) + "." + " " + tasks.getTask(i));
            }
            showMessage("Found " + tasks.getSize() + " task(s).");
        }
    }

    /**
     * Prints a horizontal line separator in the console.
     */
    public void drawHorizontalLine() {
        int length = 50;
        System.out.println("-".repeat(length));
    }
}
