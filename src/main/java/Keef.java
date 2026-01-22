import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Keef {
    public static void main(String[] args) {
        // Scanner object to read input from the keyboard
        Scanner sc = new Scanner(System.in);
        String userInput = "";

        // ArrayList to store userInput
        List<Task> tasks = new ArrayList<>();

        // Hello greeting
        drawHorizontalLine();
        System.out.println("Hello! I'm Keef!\nWhat can I do for you? ~");
        drawHorizontalLine();

        // Conversation logic
        while (true) {
            System.out.print("You:");

            try {
                userInput = sc.nextLine();

                // Get user input and vary response according to it
                String[] parts = userInput.trim().split(" ", 2);
                CommandType commandType = CommandType.fromString(parts[0]);
                String arguments = parts.length > 1 ? parts[1] : "";

                drawHorizontalLine();
                System.out.print("Keef: ");

                switch (commandType) {
                    case BYE:
                        // Bye greeting
                        System.out.println("See ya soon! ~");
                        drawHorizontalLine();
                        return;
                    case LIST:
                        // List out all items stored
                        if (tasks.isEmpty()) {
                            System.out.println("You don't have any tasks yet. Start adding!");
                        } else {
                            System.out.println("Here are the tasks in your list:");
                            printTasks(tasks);
                        }
                        drawHorizontalLine();
                        continue;
                    case MARK:
                    case UNMARK:
                    case DELETE:
                        // Get the task number to be marked / unmarked / deleted
                        int taskIndex = Integer.parseInt(arguments);
                        // Error Handling
                        boolean outOfBounds = taskIndex <= 0 || taskIndex - 1 >= tasks.size();
                        if (outOfBounds) {
                            throw new KeefException("Uhm bro, you " + (tasks.isEmpty() ? "" : "only ") + "have " + tasks.size() + " task(s) in your list.");
                        }
                        Task selectedTask = tasks.get(taskIndex - 1);

                        // Mark / Unmark / Delete a Task
                        if (commandType == CommandType.MARK) {
                            if (selectedTask.isDone()) {
                                throw new KeefException("You are already done with this task!");
                            }
                            selectedTask.markAsDone();
                        } else if (commandType == CommandType.UNMARK) {
                            if (!selectedTask.isDone()) {
                                throw new KeefException("You didn't mark this task to begin with!");
                            }
                            selectedTask.markAsUndone();
                        } else {
                            tasks.remove(selectedTask);
                        }

                        printMessage(selectedTask, tasks.size(), commandType);
                        continue;
                    case TODO:
                        // Error Handling
                        if (arguments.isEmpty()) {
                            throw new KeefException("Bro, you left out what exactly you wanted to do! Add something!");
                        }

                        // Add new ToDo Task
                        Task new_todo = new ToDo(arguments);
                        tasks.add(new_todo);
                        printMessage(new_todo, tasks.size(), CommandType.ADD);
                        continue;
                    case DEADLINE:
                        // Error Handling
                        if (!arguments.contains("/by")) {
                            throw new KeefException("Bro, you must include /by <date>");
                        }
                        String[] deadlineParts = arguments.split("/by", 2);
                        if (deadlineParts[0].isEmpty() || deadlineParts[1].isEmpty()) {
                            throw new KeefException("Bro, the description and date can't be empty!");
                        }

                        // Add new Deadline Task
                        Task deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                        tasks.add(deadline);
                        printMessage(deadline, tasks.size(), CommandType.ADD);
                        continue;
                    case EVENT:
                        // Error Handling
                        if (!arguments.contains("/from") || !arguments.contains("/to")) {
                            throw new KeefException("Bro, you must include /from <start> and /to <end>.");
                        }
                        String[] eventParts = arguments.split("/from|/to");

                        if (eventParts.length < 3 ||
                                eventParts[0].isEmpty() ||
                                eventParts[1].isEmpty() ||
                                eventParts[2].isEmpty()) {
                            throw new KeefException("Bro, you the event description, start, and end cannot be empty!");
                        }

                        // Add new Event Task
                        Task event = new Event(eventParts[0], eventParts[1], eventParts[2]);
                        tasks.add(event);
                        printMessage(event, tasks.size(), CommandType.ADD);
                        continue;
                    default:
                        // Shouldn't reach here
                }
            }
            catch (KeefException e) {
                System.out.println(e.getMessage());
                drawHorizontalLine();
            }
        }
    }

    public static void drawHorizontalLine() {
        int length = 50;
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void printTasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i+1 + "." + " " + tasks.get(i));
        }
    }
    
    public static void printMessage(Task task, int size, CommandType type) {
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
}
