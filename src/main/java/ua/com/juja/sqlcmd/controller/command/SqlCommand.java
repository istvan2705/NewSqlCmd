package ua.com.juja.sqlcmd.controller.command;

public enum SqlCommand {
    CONNECT("connect"), CREATE("create"), CLEAR("clear"), DELETE("delete"),
    DROP("drop"), EXIT("exit"), FIND("find"), HELP("help"),
    INSERT("insert"), TABLES("tables"), UPDATE("update");

    private final String command;

    SqlCommand(String command) {
        this.command = command;
    }

    public String getSqlCommand() {
        return command;
    }

}