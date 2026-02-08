package keef.parser;

import javafx.stage.Stage;
import keef.command.AddDeadlineCommand;
import keef.command.AddEventCommand;
import keef.command.AddTodoCommand;
import keef.command.ByeCommand;
import keef.command.Command;
import keef.command.CommandType;
import keef.command.DeleteCommand;
import keef.command.FindCommand;
import keef.command.ListCommand;
import keef.command.MarkCommand;
import keef.command.UnmarkCommand;
import keef.exception.KeefException;

/**
 * Parser is responsible for interpreting user input strings and converting
 * them into corresponding Command objects that can be executed by the application.
 */
public class Parser {
    /**
     * Parses the user's full input and returns the corresponding {@link Command}.
     *
     * <p>This method orchestrates the parsing process by extracting the command word,
     * the arguments, determining the {@link CommandType}, and creating the appropriate
     * {@link Command} object.</p>
     *
     * @param fullCommand the full user input string; must not be null
     * @param stage the JavaFX stage for commands that require UI access
     * @return a {@link Command} corresponding to the input
     * @throws KeefException if the command word is unknown or invalid
     */
    public static Command parse(String fullCommand, Stage stage) throws KeefException {
        assert fullCommand != null : "Full command should not be null";

        String[] commandParts = fullCommand.trim().split(" ", 2);

        String commandWord = extractCommandWord(commandParts);
        String arguments = extractArguments(commandParts);

        CommandType type = CommandType.fromString(commandWord);

        return createCommand(type, arguments, stage);
    }

    /**
     * Extracts the command word from the split command parts.
     *
     * @param commandParts array from splitting the full input
     * @return the command word in uppercase
     */
    private static String extractCommandWord(String[] commandParts) {
        assert commandParts.length > 0 : "Command parts should have at least one element";
        String commandWord = commandParts[0].toUpperCase();
        assert !commandWord.isBlank() : "Command word should not be blank";
        return commandWord;
    }

    /**
     * Extracts the arguments from the split command parts.
     *
     * @param commandParts array from splitting the full input
     * @return the arguments string, or empty if none
     */
    private static String extractArguments(String[] commandParts) {
        return commandParts.length > 1 ? commandParts[1] : "";
    }

    /**
     * Creates a {@link Command} object corresponding to the specified {@link CommandType}.
     *
     * @param type the type of command to create
     * @param arguments the arguments for the command
     * @param stage the JavaFX stage, used by commands that interact with the UI
     * @return a {@link Command} instance corresponding to the type
     * @throws KeefException if the command type is not recognized
     */
    private static Command createCommand(CommandType type, String arguments, Stage stage) throws KeefException {
        return switch (type) {
            //CHECKSTYLE.OFF: Indentation
            case BYE -> new ByeCommand(stage);
            case LIST -> new ListCommand();
            case TODO -> new AddTodoCommand(arguments);
            case DEADLINE -> new AddDeadlineCommand(arguments);
            case EVENT -> new AddEventCommand(arguments);
            case MARK -> new MarkCommand(arguments);
            case UNMARK -> new UnmarkCommand(arguments);
            case DELETE -> new DeleteCommand(arguments);
            case FIND -> new FindCommand(arguments);
            default -> throw new KeefException("Huh, what do you mean?");
            //CHECKSTYLE.ON: Indentation
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
    public static int parseTaskIndex(String arguments, int max) throws KeefException {
        assert arguments != null : "Arguments should not be null";
        assert max >= 0 : "Max task count should not be negative";

        int taskIndex;
        try {
            taskIndex = Integer.parseInt(arguments.trim());
        } catch (NumberFormatException e) {
            throw new KeefException("Uhm bro, that's not a valid task number!");
        }

        boolean isOutOfBounds = taskIndex <= 0 || taskIndex > max;
        if (isOutOfBounds) {
            throw new KeefException("Uhm bro, you only have " + max + " task(s) in your list.");
        }

        return taskIndex;
    }
}

