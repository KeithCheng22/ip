import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Keef {
    public static void main(String[] args) {
        File dataFile = new File("./data/keef.txt");
        Ui ui = new Ui();

        // ArrayList to store userInput
        List<Task> tasks = loadTasks(dataFile);

        ui.showWelcome();
        boolean isExit = false;
        // Conversation logic
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, dataFile);
                isExit = command.isExit();
            } catch (KeefException e) {
                System.out.println(e.getMessage());
                ui.drawHorizontalLine();
            }
        }
    }

    public static List<Task> loadTasks(File dataFile) {
        List<Task> tasks = new ArrayList<>();

        if (!dataFile.exists()) {
            return tasks;
        }

        try (Scanner sc = new Scanner(dataFile)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split("\\|");

                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                Task task = switch (type) {
                    case "T" -> new ToDo(parts[2]);
                    case "D" -> new Deadline(parts[2], LocalDateTime.parse(parts[3]));
                    case "E" -> new Event(parts[2],  LocalDateTime.parse(parts[3]),  LocalDateTime.parse(parts[3]));
                    default -> null;
                };

                if (task != null && isDone) {
                    task.markAsDone();
                }

                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return tasks;
    }

    public static void saveTasks(List<Task> tasks, File dataFile) {
        try {
            File folder = dataFile.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            FileWriter writer = new FileWriter(dataFile);
            for (Task task : tasks) {
                String line = "";
                if (task instanceof ToDo) {
                    line = "T | " + (task.isDone() ? "1" : "0") + " | " + task.description;
                } else if (task instanceof Deadline) {
                    Deadline d = (Deadline) task;
                    line = "D | " + (d.isDone() ? "1" : "0") + " | " + d.description + " | " + d.by;
                } else if (task instanceof Event) {
                    Event e = (Event) task;
                    line = "E | " + (e.isDone() ? "1" : "0") + " | " + e.description + " | " + e.from + " | " + e.to;
                }

                writer.write(line + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
