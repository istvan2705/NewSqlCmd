package ua.com.juja.sqlcmd;

public interface Command {

    boolean canProcess(String command );

    void process(String command);


}
