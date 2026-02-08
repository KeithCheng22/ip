package keef.task;

import java.time.LocalDateTime;

/**
 * Represents a task that has a deadline.
 * Extends the {@link Task} class.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Constructs a new Deadline task with the specified description and deadline.
     *
     * @param description the description of the task
     * @param by the deadline date and time
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline of this task.
     *
     * @return the {@link LocalDateTime} representing the deadline
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Returns a string representation of this Deadline for storage.
     *
     * <p>Format: D | status | description | by
     * where status is "1" if done, "0" otherwise, and by is formatted using {@link #STORAGE_FORMAT}.</p>
     *
     * @return storage-friendly string for this Deadline
     */
    @Override
    public String toStorageString() {
        return "D | " + (isDone() ? "1" : "0") + " | " + getDescription() + " | " + getBy().format(STORAGE_FORMAT);
    }

    /**
     * Returns a string representation of the task for display purposes.
     *
     * @return formatted string including type, description, and deadline
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }
}
