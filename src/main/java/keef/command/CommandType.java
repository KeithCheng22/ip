package keef.command;

import keef.exception.KeefException;

/**
 * Represents the supported command types in the Keef application.
 * Each enum value corresponds to a valid user command.
 */
public enum CommandType {
    LIST,
    BYE,
    TODO,
    DEADLINE,
    EVENT,
    MARK,
    UNMARK,
    DELETE,
    ADD,
    FIND;

    /**
     * Converts a string into its corresponding {@code CommandType}.
     *
     * @param commandType the command word entered by the user
     * @return the matching {@code CommandType}
     * @throws KeefException if the command type is invalid or unsupported
     */
    public static CommandType fromString(String commandType) throws KeefException {
        try {
            return CommandType.valueOf(commandType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new KeefException("Keef: Huh, what do you mean?");
        }
    }
}
