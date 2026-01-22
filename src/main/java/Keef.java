public class Keef {
    public static void main(String[] args) {
        draw_horizontal_line();
        System.out.println("Hello! I'm Keef!\nWhat can I do for you?");
        draw_horizontal_line();
        System.out.println("See ya soon!");
    }

    public static void draw_horizontal_line() {
        int length = 50;
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
