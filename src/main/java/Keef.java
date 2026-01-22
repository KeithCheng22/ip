import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Keef {
    public static void main(String[] args) {
        // Scanner object to read input from the keyboard
        Scanner sc = new Scanner(System.in);
        String user_input = "";
        List<Task> tasks = new ArrayList<>();

        // Hello greeting
        draw_horizontal_line();
        System.out.println("Hello! I'm Keef!\nWhat can I do for you? ~");
        draw_horizontal_line();

        // Conversation logic
        while (true) {
            System.out.print("You: ");

            // Get user input and vary response according to it
            user_input = sc.nextLine();
            String[] parts = user_input.split(" ");
            String command = parts[0];

            switch (command) {
                case "bye":
                    // Bye greeting
                    draw_horizontal_line();
                    System.out.print("Keef: ");
                    System.out.println("See ya soon! ~");
                    draw_horizontal_line();
                    break;
                case "list":
                    // List out all items stored
                    draw_horizontal_line();
                    System.out.println("Here are the tasks in your list:");
                    print_tasks(tasks);
                    draw_horizontal_line();
                    continue;
                case "mark":
                case "unmark":
                    // Get the task number to be marked / unmarked
                    int task_index = Integer.parseInt(parts[1]);
                    Task selected_task = tasks.get(task_index - 1);

                    // Mark a task as done or undone
                    if (command.equals("mark")) {
                        selected_task.markAsDone();
                        draw_horizontal_line();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(selected_task);
                        draw_horizontal_line();
                    } else {
                        selected_task.markAsUndone();
                        draw_horizontal_line();
                        System.out.println("OK! I've marked this task as not done yet:");
                        System.out.println(selected_task);
                        draw_horizontal_line();
                    }

                    continue;
                default:
                    // Add user_input to items list
                    draw_horizontal_line();
                    System.out.print("Keef: ");
                    Task new_task = new Task(user_input);
                    tasks.add(new_task);
                    System.out.println("Added" + " " + "'" + new_task.description + "'");
                    draw_horizontal_line();
                    continue;
            }

            break;
        }
    }

    public static void draw_horizontal_line() {
        int length = 50;
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void print_tasks(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(i+1 + "." + " " + tasks.get(i));
        }
    }
}
