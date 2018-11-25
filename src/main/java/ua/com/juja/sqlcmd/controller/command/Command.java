package ua.com.juja.sqlcmd.controller.command;


public interface Command {
    String ERROR_ENTERING_MESSAGE = "Error entering DatabaseManagerMockitoTest, it should be";
    String SQL_EXCEPTION_MESSAGE = "Can not execute DatabaseManagerMockitoTest due to: %s";

    void execute();
}
