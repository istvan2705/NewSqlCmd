package ua.com.juja.sqlcmd.controller.command;

public enum SqlCommands {
    DELETE("delete"), UPDATE("update"), CREATE("create"),
    INSERT("insert");

    private final String command;

    SqlCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}

