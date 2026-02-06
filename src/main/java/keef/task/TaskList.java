package keef.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks and provides operations to manage them.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Constructs an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Constructs a task list using an existing list of tasks.
     *
     * @param tasks the list of tasks to initialize with
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Removes the specified task from the list.
     *
     * @param task the task to be removed
     */
    public void deleteTask(Task task) {
        tasks.remove(task);
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to be added
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param taskIndex the index of the task
     * @return the task at the given index
     */
    public Task getTask(int taskIndex) {
        return tasks.get(taskIndex);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the total number of tasks
     */
    public int getSize() {
        return tasks.size();
    }

    /**
     * Returns all tasks in the list.
     *
     * @return the list of tasks
     */
    public List<Task> getAllTasks() {
        return tasks;
    }
}
