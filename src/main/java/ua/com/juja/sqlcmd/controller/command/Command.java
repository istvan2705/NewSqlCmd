package ua.com.juja.sqlcmd.controller.command;


public interface Command {

    String SQL_EXCEPTION_MESSAGE = "Can not execute command  due to: %s";

    boolean canProcess(String command);

    void process(String command);
}
