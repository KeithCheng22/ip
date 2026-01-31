package keef.storage;

import keef.task.Deadline;
import keef.task.Event;
import keef.task.Task;
import keef.task.TaskList;
import keef.task.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    private final TaskList tasks;

    public Storage(String filePath) {
        this.filePath = filePath;
        this.tasks = loadTasks();
    }

    public TaskList loadTasks() {
        File dataFile = new File(filePath);
        TaskList tasks = new TaskList();

        if (!dataFile.exists()) {
            return new TaskList();
        }

        try (Scanner sc = new Scanner(dataFile)) {
            while (sc.hasNextLine()) {
                // Read each task in the datafile
                String line = sc.nextLine();
                String[] parts = line.split("\\|");

                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }

                // Get the details of each task
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                Task task = switch (type) {
                    case "T" -> new ToDo(parts[2]);
                    case "D" -> new Deadline(parts[2], LocalDateTime.parse(parts[3]));
                    case "E" -> new Event(parts[2],  LocalDateTime.parse(parts[3]),  LocalDateTime.parse(parts[3]));
                    default -> null;
                };

                // Mark task if it is done
                if (task != null && isDone) {
                    tasks.markTask(task);
                }

                // Add task if it is not null
                if (task != null) {
                    tasks.addTask(task);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return tasks;
    }

    public void saveTasks() {
        File dataFile = new File(filePath);
        try {
            File folder = dataFile.getParentFile();

            // Create a new folder if it does not exist
            if (!folder.exists()) {
                folder.mkdirs();
            }

            FileWriter writer = new FileWriter(dataFile);

            // Extract and write details about the task into the datafile
            for (Task task : tasks.getAllTasks()) {
                String line = "";
                if (task instanceof ToDo) {
                    line = "T | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription();
                } else if (task instanceof Deadline) {
                    Deadline d = (Deadline) task;
                    line = "D | " + (d.isDone() ? "1" : "0") + " | " + d.getDescription() + " | " + d.getBy();
                } else if (task instanceof Event) {
                    Event e = (Event) task;
                    line = "E | " + (e.isDone() ? "1" : "0") + " | " + e.getDescription() + " | "
                            + e.getFrom() + " | " + e.getTo();
                }

                writer.write(line + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public TaskList getTasks() {
        return tasks;
    }
}
