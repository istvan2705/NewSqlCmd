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

    private String command;

    SqlCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static SqlCommand getSqlCommand(String text) {
        SqlCommand command;
        for (SqlCommand com : SqlCommand.values()) {
            if (com.command.equalsIgnoreCase(text)) {
                command = com;

                return command;
            }
        }
        throw new IllegalArgumentException();
    }
}