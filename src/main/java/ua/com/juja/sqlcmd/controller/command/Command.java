package ua.com.juja.sqlcmd.controller.command;


public interface Command {
    String ERROR_ENTERING_MESSAGE = "Error entering command '%s', it should be";
    String SQL_EXCEPTION_MESSAGE = "Can not execute command due to: %s";

    void process(String command);
}
