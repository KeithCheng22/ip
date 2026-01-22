import java.util.Scanner;

public class Keef {
    public static void main(String[] args) {
        // Scanner object to read input from the keyboard
        Scanner sc = new Scanner(System.in);
        String user_input = "";

        // Hello greeting
        draw_horizontal_line();
        System.out.println("Hello! I'm Keef!\nWhat can I do for you? ~");
        draw_horizontal_line();

        // Conversation logic
        while (true) {
            System.out.print("You: ");

            // Get user input
            user_input = sc.nextLine();

            if (user_input.equals("bye")) {
                break;
            } else {
                draw_horizontal_line();
                System.out.print("Keef: ");
                System.out.println(user_input);
                draw_horizontal_line();
            }
        }

        // Bye greeting
        draw_horizontal_line();
        System.out.print("Keef: ");
        System.out.println("See ya soon! ~");
        draw_horizontal_line();
    }

    public static void draw_horizontal_line() {
        int length = 50;
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
