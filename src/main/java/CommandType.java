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
        Ui ui = new Ui();
        try {
            return CommandType.valueOf(commandType.toUpperCase());
        } catch (IllegalArgumentException e) {
            ui.drawHorizontalLine();
            throw new KeefException("Keef: Huh, what do you mean?");
        }
    }
}
