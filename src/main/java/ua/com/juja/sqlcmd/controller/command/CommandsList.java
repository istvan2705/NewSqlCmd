package ua.com.juja.sqlcmd.controller.command;

public enum CommandsList {
    DELETE("delete"), UPDATE("update"), CREATE("create"),
    INSERT("insert");

    private final String command;

    CommandsList(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}

