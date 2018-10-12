package ua.com.juja.sqlcmd.controller.command;

public enum SqlCommand {
    connect("connect"), create("create"), clear("clear"), delete("delete"),
    drop("drop"), exit("exit"),find("find"), help("help"),
    insert("insert"), tables("tables"), update("update");

    private final String command;

    SqlCommand(String command) {
        this.command = command;
    }

    public String getCommandName() {
        return command;
    }

}