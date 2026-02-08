package keef.ui;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import keef.command.CommandType;
import keef.task.Task;
import keef.task.TaskList;

/**
 * Ui handles all interactions with the user via the console.
 * It is responsible for reading user input and printing messages,
 * including task lists, errors, and system responses.
 */
public class Ui {
    /**
     * Constructs an Ui object and initializes the input scanner.
     */
    public Ui() {}

    /**
     * Prints the welcome message to the user.
     */
    public String showWelcome() {
        return "Hello! I'm Keef!\nWhat can I do for you? ~";
    }

    /**
     * Prints all tasks in a TaskList with their numbering.
     *
     * @param tasks the TaskList to display
     */
    public String printTasks(TaskList tasks) {
        if (tasks.getSize() == 0) {
            return "You don't have any tasks yet. Start adding!";
        }

        return formatTaskList(tasks, "Here are the tasks in your list:\n");
    }

    /**
     * Prints a confirmation message for task actions such as add, delete, mark, or unmark.
     *
     * @param task the task affected
     * @param size the current size of the task list
     * @param type the type of command performed
     */
    public String printMessage(Task task, int size, CommandType type) {
        //CHECKSTYLE.OFF: Indentation
        String pastTenseType = switch (type) {
            case ADD -> "added";
            case DELETE -> "deleted";
            case MARK -> "marked";
            case UNMARK -> "unmarked";
            default -> "";
        };
        //CHECKSTYLE.ON: Indentation
        return "Got it. I've " + pastTenseType + " this task:\n"
                + task + "\n"
                + "Now you have " + size + " tasks in your list.";
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
    public String printFoundTasks(TaskList tasks) {
        if (tasks.getSize() == 0) {
            return "No matching tasks found.";
        }

        return formatTaskList(tasks, "Here are the matching tasks in your list:\n")
                + "Found " + tasks.getSize() + " task(s).";
    }

    /**
     * Formats a TaskList into a numbered list string.
     *
     * @param tasks the TaskList to format
     * @param header a string to prepend to the task list (e.g., a message)
     * @return a formatted numbered string of tasks
     */
    private String formatTaskList(TaskList tasks, String header) {
        String taskBody = IntStream.range(0, tasks.getSize())
                .mapToObj(i -> (i + 1) + ". " + tasks.getTask(i))
                .collect(Collectors.joining("\n"));

        return header + taskBody + "\n";
    }
}
