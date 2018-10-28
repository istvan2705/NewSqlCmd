package ua.com.juja.sqlcmd.controller.command;

public enum SqlCommand {
    CONNECT("connect"),
    CREATE("create"),
    CLEAR("clear"),
    DELETE("delete"),
    DROP("drop"),
    EXIT("exit"),
    FIND("find"),
    HELP("help"),
    INSERT("insert"),
    TABLES("tables"),
    UPDATE("update");

    private String value;

    SqlCommand(String value) {
        this.value = value;
    }

    public String getCommand() {
        return value;
    }

    public static SqlCommand getSqlCommand(String value) {
        if (value != null) {
            for (SqlCommand command : SqlCommand.values()) {
                if (value.equalsIgnoreCase(command.value)) {
                    return command;
                }
            }
        }
        throw new IllegalArgumentException();
    }
}