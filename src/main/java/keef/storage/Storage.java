package keef.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

import keef.task.Deadline;
import keef.task.Event;
import keef.task.Task;
import keef.task.TaskList;
import keef.task.ToDo;

/**
 * Storage handles loading and saving tasks to a persistent file.
 * It maintains a TaskList internally and provides methods
 * to read/write tasks from/to a file.
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a Storage object with the given file path.
     * It automatically loads existing tasks from the file into memory.
     *
     * @param filePath the path to the file used for storing tasks
     */
    public Storage(String filePath) {
        assert filePath != null : "File path should not be null";
        assert !filePath.isBlank() : "File path should not be blank";

        this.filePath = filePath;
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

                // Get the details of each task
                Task task = getTask(parts);

                if (task != null) {
                    tasks.addTask(task);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return tasks;
    }

    private static Task getTask(String[] parts) {
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        Task task = switch (type) {
            //CHECKSTYLE.OFF: Indentation
            case "T" -> {
                assert parts.length == 3 : "ToDo should have exactly 3 fields";
                yield new ToDo(parts[2]);
            }
            case "D" -> {
                assert parts.length == 4 : "Deadline should have exactly 4 fields";
                yield new Deadline(parts[2], LocalDateTime.parse(parts[3]));
            }
            case "E" -> {
                assert parts.length == 5 : "Event should have exactly 5 fields";
                yield new Event(parts[2],
                        LocalDateTime.parse(parts[3]),
                        LocalDateTime.parse(parts[4]));
            }
            default -> null;
            //CHECKSTYLE.ON: Indentation
        };

        // Mark task if it is done
        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Saves all tasks in the TaskList to the data file.
     * Each task is written in a pipe-separated format:
     * <code>Type | Status | Description | [Dates]</code>.
     */
    public void saveTasks(TaskList tasks) {
        File dataFile = new File(filePath);
        try {
            File folder = dataFile.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            FileWriter writer = new FileWriter(dataFile);

            for (Task task : tasks.getAllTasks()) {
                writer.write(task.toStorageString() + System.lineSeparator());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
