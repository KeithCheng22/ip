package keef.parser;

import keef.command.AddDeadlineCommand;
import keef.command.AddEventCommand;
import keef.command.AddTodoCommand;
import keef.command.ByeCommand;
import keef.command.Command;
import keef.command.CommandType;
import keef.command.DeleteCommand;
import keef.command.ListCommand;
import keef.command.MarkCommand;
import keef.command.UnmarkCommand;
import keef.exception.KeefException;

public class Parser {
    public static Command parse(String fullCommand) throws KeefException {
        String[] parts = fullCommand.trim().split(" ", 2);
        String commandWord = parts[0].toUpperCase();
        String arguments = parts.length > 1 ? parts[1] : "";

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
            default -> throw new KeefException("Huh, what do you mean?");
        };
    }

    public static int parseTaskIndex (String arguments, int max) throws KeefException {
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
