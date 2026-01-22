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
            Keef.drawHorizontalLine();
            throw new KeefException("Keef: Huh, what do you mean?");
        }
    }
}
