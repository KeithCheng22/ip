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
     * Returns the string representation of the todo task.
     *
     * @return formatted todo task string
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
