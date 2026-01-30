public enum CommandType {
    LIST,
    BYE,
    TODO,
    DEADLINE,
    EVENT,
    MARK,
    UNMARK,
    DELETE,
    ADD;

    public static CommandType fromString(String commandType) throws KeefException {
        try {
            return CommandType.valueOf(commandType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new KeefException("Huh, what do you mean?");
        }
    }
}
