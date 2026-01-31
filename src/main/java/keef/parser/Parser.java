package keef.parser;

import keef.command.*;
import keef.exception.KeefException;

/**
 * Parser is responsible for interpreting user input strings and converting
 * them into corresponding Command objects that can be executed by the application.
 */
public class Parser {

    /**
     * Parses a full command string entered by the user and returns the corresponding Command.
     *
     * @param fullCommand the full user input string
     * @return the Command object corresponding to the user input
     * @throws KeefException if the command is invalid or unrecognized
     */
    public static Command parse(String fullCommand) throws KeefException {
        // Split the command into its arguments
        String[] parts = fullCommand.trim().split(" ", 2);
        String commandWord = parts[0].toUpperCase();
        String arguments = parts.length > 1 ? parts[1] : "";

        // Get the command type to execute
        CommandType type = CommandType.fromString(commandWord);

        return switch (type) {
            case BYE -> new ByeCommand();
            case LIST -> new ListCommand();
            case TODO -> new AddTodoCommand(arguments);
            case DEADLINE -> new AddDeadlineCommand(arguments);
            case EVENT -> new AddEventCommand(arguments);
            case MARK -> new MarkCommand(arguments);
            case UNMARK -> new UnmarkCommand(arguments);
            case DELETE -> new DeleteCommand(arguments);
            case FIND -> new FindCommand(arguments);
            default -> throw new KeefException("Huh, what do you mean?");
        };
    }

    /**
     * Parses a string representing a task index and validates it against the maximum allowed.
     *
     * @param arguments the string containing the task index
     * @param max the maximum allowed task index
     * @return the parsed task index as an integer
     * @throws KeefException if the string is not a valid number or is out of bounds
     */
    public static int parseTaskIndex (String arguments, int max) throws KeefException {
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(arguments.trim());
        } catch (NumberFormatException e) {
            throw new KeefException("Uhm bro, that's not a valid task number!");
        }

        // If taskIndex is OOB
        boolean isOutOfBounds = taskIndex <= 0 || taskIndex > max;
        if (isOutOfBounds) {
            throw new KeefException("Uhm bro, you only have " + max + " task(s) in your list.");
        }

        return taskIndex;
    }
}
