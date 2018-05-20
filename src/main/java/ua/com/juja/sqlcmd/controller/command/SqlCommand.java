package ua.com.juja.sqlcmd.controller.command;

public enum SqlCommand {
    DELETE("delete"), UPDATE("update"), CREATE("create"),
    INSERT("insert");

    private final String command;

    SqlCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}

