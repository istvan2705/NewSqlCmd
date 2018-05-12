package ua.com.juja.sqlcmd;

public interface Command {

    String SEPARATOR = "\\|";

    String SQL_EXCEPTION_MESSAGE ="Can not execute command  due to: %s";

    boolean canProcess(String command );

    void process(String command);
}
