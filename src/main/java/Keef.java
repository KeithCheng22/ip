import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Keef {
    public static void main(String[] args) {
        // Scanner object to read input from the keyboard
        Scanner sc = new Scanner(System.in);
        String userInput = "";
        List<Task> tasks = new ArrayList<>();

        // Hello greeting
        drawHorizontalLine();
        System.out.println("Hello! I'm Keef!\nWhat can I do for you? ~");
        drawHorizontalLine();

        // Conversation logic
        while (true) {
            System.out.print("You: ");

            // Get user input and vary response according to it
            userInput = sc.nextLine();
            String[] parts = userInput.split(" ", 2);
            String command = parts[0];
            String arguments  = parts.length > 1 ? parts[1] : "";

            drawHorizontalLine();
            System.out.print("Keef: ");

            switch (command) {
                case "bye":
                    // Bye greeting
                    System.out.println("See ya soon! ~");
                    drawHorizontalLine();
                    return;
                case "list":
                    // List out all items stored
                    System.out.println("Here are the tasks in your list:");
                    printTasks(tasks);
                    drawHorizontalLine();
                    continue;
                case "mark":
                case "unmark":
                    // Get the task number to be marked / unmarked
                    int taskIndex = Integer.parseInt(arguments);
                    Task selectedTask = tasks.get(taskIndex - 1);

                    // Mark a task as done or undone
                    boolean done = command.equals("mark");
                    if (done) {
                        selectedTask.markAsDone();
                    } else {
                        selectedTask.markAsUndone();
                    }

                    System.out.println("OK! I've marked this task as " + (done ? "done: " : "not done yet:"));
                    System.out.println(selectedTask);
                    drawHorizontalLine();
                    continue;
                case "todo":
                    Task new_todo = new ToDo(arguments);
                    tasks.add(new_todo);
                    printAddMessage(new_todo, tasks.size());
                    continue;
                case "deadline":
                    String[] deadlineParts = arguments.split("/by");
                    Task deadline = new Deadline(deadlineParts[0], deadlineParts[1]);
                    tasks.add(deadline);
                    printAddMessage(deadline, tasks.size());
                    continue;
                case "event":
                    String[] eventParts = arguments.split("/from|/to");
                    Task event = new Event(eventParts[0], eventParts[1], eventParts[2]);
                    tasks.add(event);
                    printAddMessage(event, tasks.size());
                    continue;
                default:
                    // Invalid command
                    System.out.println("Huh, what do you mean?");
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
    
    public static void printAddMessage(Task task, int size) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        System.out.println("Now you have " + size + " tasks in your list.");
        drawHorizontalLine();
    }
}
