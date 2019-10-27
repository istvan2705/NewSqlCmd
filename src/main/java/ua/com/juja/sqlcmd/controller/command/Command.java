package ua.com.juja.sqlcmd.controller.command;


import java.sql.SQLException;

public interface Command {
    String ERROR_ENTERING_MESSAGE = "Error entering command, it should be";

    void execute(String command) throws SQLException;
}
