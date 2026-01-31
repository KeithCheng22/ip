package keef.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start and end time.
 * Extends the {@link Task} class.
 */
public class Event extends Task  {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs a new Event task with the specified description, start time, and end time.
     *
     * @param description the description of the event
     * @param from the start date and time of the event
     * @param to the end date and time of the event
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    /**
     * Returns the start date and time of this event.
     *
     * @return the {@link LocalDateTime} representing the start of the event
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Returns the end date and time of this event.
     *
     * @return the {@link LocalDateTime} representing the end of the event
     */
    public LocalDateTime getTo() {
        return to;
    }

    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Returns a string representation of the event for display purposes.
     *
     * @return formatted string including type, description, start, and end times
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT)
                + " to: " + to.format(OUTPUT_FORMAT) + ")";
    }
}
