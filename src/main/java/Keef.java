import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Keef {
    public static void main(String[] args) {
        // Scanner object to read input from the keyboard
        Scanner sc = new Scanner(System.in);
        String user_input = "";
        List<String> items = new ArrayList<>();

        // Hello greeting
        draw_horizontal_line();
        System.out.println("Hello! I'm Keef!\nWhat can I do for you? ~");
        draw_horizontal_line();

        // Conversation logic
        while (true) {
            System.out.print("You: ");

            // Get user input and vary response according to it
            user_input = sc.nextLine();
            switch (user_input) {
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
                    print_items(items);
                    draw_horizontal_line();
                    continue;
                default:
                    // Add user_input to items list
                    draw_horizontal_line();
                    System.out.print("Keef: ");
                    items.add(user_input);
                    System.out.println("Added" + " " + "'" + user_input + "'");
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

    public static void print_items(List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            System.out.println(i+1 + "." + " " + items.get(i));
        }
    }
}
