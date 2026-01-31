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

/**
 * Storage handles loading and saving tasks to a persistent file.
 * It maintains a TaskList internally and provides methods
 * to read/write tasks from/to a file.
 */
public class Storage {
    private String filePath;
    private TaskList tasks;

    /**
     * Constructs a Storage object with the given file path.
     * It automatically loads existing tasks from the file into memory.
     *
     * @param filePath the path to the file used for storing tasks
     */
    public Storage(String filePath) {
        this.filePath = filePath;
        this.tasks = loadTasks();
    }

    /**
     * Loads tasks from the data file into a TaskList.
     * If the file does not exist, an empty TaskList is returned.
     *
     * @return a TaskList containing all tasks from the file
     */
    public TaskList loadTasks() {
        File dataFile = new File(filePath);
        TaskList tasks = new TaskList();

        if (!dataFile.exists()) {
            return new TaskList();
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
                    tasks.markTask(task);
                }

                if (task != null) {
                    tasks.addTask(task);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves all tasks in the TaskList to the data file.
     * Each task is written in a pipe-separated format:
     * <code>Type | Status | Description | [Dates]</code>.
     */
    public void saveTasks() {
        File dataFile = new File(filePath);
        try {
            File folder = dataFile.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            FileWriter writer = new FileWriter(dataFile);
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
