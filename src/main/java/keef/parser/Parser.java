package keef.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

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

        // Used ChatGPT to explore improvements to string splitting.
        // Adopted "\\s+" to correctly handle multiple spaces in user input.
        String[] commandParts = fullCommand.trim().split("\\s+");

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
        if (commandParts.length <= 1) {
            return "";
        }

        return String.join(" ", Arrays.copyOfRange(commandParts, 1, commandParts.length));
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
     * Parses a string representing one or more task indices and validates them
     * against the maximum allowed task count.
     *
     * <p>The input string can contain:</p>
     * <ul>
     *     <li>A single task number, e.g., "3"</li>
     *     <li>Multiple space-separated numbers, e.g., "1 2 5"</li>
     *     <li>Ranges of numbers using a dash, e.g., "3-6"</li>
     *     <li>The keyword "all" to select all tasks from 1 to {@code maxTaskCount}</li>
     * </ul>
     *
     * <p>Duplicates are automatically removed and the returned list is sorted
     * in ascending order.</p>
     *
     * @param arguments the string containing task indices, ranges, or "all"
     * @param maxTaskCount the maximum valid task index (i.e., the number of tasks in the list)
     * @return a list of valid task indices
     * @throws KeefException if any number is invalid, out of bounds, or if a range is malformed
     */
    public static List<Integer> parseTaskIndices(String arguments, int maxTaskCount) throws KeefException {
        assert arguments != null : "Arguments should not be null";
        assert maxTaskCount >= 0 : "Max task count should not be negative";

        // Used ChatGPT to identify edge cases where arguments may be blank and
        // added validation to throw an exception if no task numbers are provided.
        if (arguments.isBlank()) {
            throw new KeefException("Please specify task number(s).");
        }

        arguments = arguments.trim();
        if (arguments.equalsIgnoreCase("all")) {
            return new ArrayList<>(IntStream.rangeClosed(1, maxTaskCount).boxed().toList());
        }

        Set<Integer> indices = new TreeSet<>();

        for (String token : arguments.split("\\s+")) {
            indices.addAll(parseToken(token, maxTaskCount));
        }

        return new ArrayList<>(indices);
    }

    /**
     * Parses a single token from the arguments, which can be either a single
     * number or a range (e.g., "3" or "5-7").
     *
     * @param token a single space-separated token from the arguments
     * @param maxTaskCount the maximum valid task index
     * @return a list of integers represented by the token
     * @throws KeefException if the token is not a valid number or range
     */
    private static List<Integer> parseToken(String token, int maxTaskCount) throws KeefException {
        token = token.trim();

        if (token.contains("-")) {
            return parseRange(token, maxTaskCount);
        }

        return List.of(parseSingleIndex(token, maxTaskCount));
    }

    /**
     * Parses a range of task indices in the form "start-end".
     *
     * @param range a string representing a range, e.g., "2-5"
     * @param maxTaskCount the maximum valid task index
     * @return a list of integers from start to end, inclusive
     * @throws KeefException if the range is malformed, out of bounds, or start > end
     */
    private static List<Integer> parseRange(String range, int maxTaskCount) throws KeefException {
        String[] parts = range.split("-");

        if (parts.length != 2) {
            throw new KeefException("Invalid range: " + range);
        }

        int start = parseSingleIndex(parts[0], maxTaskCount);
        int end = parseSingleIndex(parts[1], maxTaskCount);

        if (start > end) {
            throw new KeefException("Invalid range: " + range);
        }

        return IntStream.rangeClosed(start, end).boxed().toList();
    }

    /**
     * Parses a single task index and validates that it falls within
     * the allowed range of 1 to {@code max}.
     *
     * @param arg the string representing a single task index
     * @param max the maximum valid task index
     * @return the parsed task index
     * @throws KeefException if the string is not a valid number or is out of bounds
     */
    private static int parseSingleIndex(String arg, int max) throws KeefException {
        int taskIndex;

        try {
            taskIndex = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            throw new KeefException("Uhm bro, that's not a valid task number!");
        }

        if (taskIndex <= 0 || taskIndex > max) {
            throw new KeefException("Uhm bro, you only have " + max + " task(s) in your list.");
        }

        return taskIndex;
    }
}

