import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Keef {
    public static void main(String[] args) {
        File dataFile = new File("./data/keef.txt");

        // Scanner object to read input from the keyboard
        Ui ui = new Ui();
        String userInput;

        // ArrayList to store userInput
        List<Task> tasks = loadTasks(dataFile);

        ui.showWelcome();

        // Conversation logic
        while (true) {
            try {
                userInput = ui.readCommand();

                // Get user input and vary response according to it
                String[] parts = userInput.trim().split(" ", 2);
                CommandType commandType = CommandType.fromString(parts[0]);
                String arguments = parts.length > 1 ? parts[1] : "";

                ui.drawHorizontalLine();
                ui.showMessage("Keef: ");

                switch (commandType) {
                case BYE:
                    // Bye greeting
                    ui.showGoodbye();
                    return;
                case LIST:
                    // List out all items stored
                    ui.printTasks(tasks);
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
                    saveTasks(tasks, dataFile);

                    ui.printMessage(selectedTask, tasks.size(), commandType);
                    continue;
                    case TODO:
                        // Error Handling
                        if (arguments.isEmpty()) {
                            throw new KeefException("Bro, you left out what exactly you wanted to do! Add something!");
                        }

                        // Add new ToDo Task
                        Task new_todo = new ToDo(arguments);
                        tasks.add(new_todo);
                        saveTasks(tasks, dataFile);
                        ui.printMessage(new_todo, tasks.size(), CommandType.ADD);
                        continue;
                    case DEADLINE:
                        // Error Handling
                        if (!arguments.contains("/by")) {
                            throw new KeefException("Bro, you must include /by <date>");
                        }
                        String[] deadlineParts = arguments.split("/by", 2);
                        String deadlineDescriptionString = deadlineParts[0].trim();
                        String dateString = deadlineParts[1].trim();
                        if (deadlineDescriptionString.isEmpty() || dateString.isEmpty()) {
                            throw new KeefException("Bro, the description and date can't be empty!");
                        }

                        DateTimeFormatter deadlineInputFormatter =
                                DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

                        // Add new Deadline Task
                        LocalDateTime by;
                        try {
                            by = LocalDateTime.parse(dateString, deadlineInputFormatter);
                        } catch (Exception e) {
                            throw new KeefException("Use date format: d/M/yyyy HHmm");
                        }

                        Task deadline = new Deadline(deadlineParts[0], by);
                        tasks.add(deadline);
                        saveTasks(tasks, dataFile);
                        ui.printMessage(deadline, tasks.size(), CommandType.ADD);
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

                        String eventDescriptionString = eventParts[0].trim();
                        String dateFromString = eventParts[1].trim();
                        String dateToString = eventParts[2].trim();

                        DateTimeFormatter eventInputFormatter =
                                DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

                        // Add new Deadline Task
                        LocalDateTime eventFrom;
                        LocalDateTime eventTo;
                        try {
                            eventFrom = LocalDateTime.parse(dateFromString, eventInputFormatter);
                            eventTo = LocalDateTime.parse(dateToString, eventInputFormatter);
                        } catch (Exception e) {
                            throw new KeefException("Use date format: d/M/yyyy HHmm");
                        }

                        // Add new Event Task
                        Task event = new Event(eventDescriptionString, eventFrom, eventTo);
                        tasks.add(event);
                        saveTasks(tasks, dataFile);
                        ui.printMessage(event, tasks.size(), CommandType.ADD);
                        continue;
                    default:
                        // Shouldn't reach here
                }
            }
            catch (KeefException e) {
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
