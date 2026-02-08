package keef.task;

/**
 * Represents a todo task without any date or time constraints.
 */
public class ToDo extends Task {
    /**
     * Constructs a todo task with the given description.
     *
     * @param description the description of the todo task
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of this ToDo for storage.
     *
     * <p>Format: T | status | description
     * where status is "1" if done, "0" otherwise.</p>
     *
     * @return storage-friendly string for this ToDo
     */
    @Override
    public String toStorageString() {
        return "T | " + (isDone() ? "1" : "0") + " | " + getDescription();
    }

    /**
     * Returns the string representation of the todo task.
     *
     * @return formatted todo task string
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
