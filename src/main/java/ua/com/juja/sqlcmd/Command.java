package ua.com.juja.sqlcmd;

import ua.com.juja.sqlcmd.model.DataSet;

import java.util.Arrays;
import java.util.List;

public interface Command {




    String SEPARATOR = "\\|";

    String SQL_EXCEPTION_MESSAGE ="Can not execute command  due to: %s";

    boolean canProcess(String command );

    void process(String command);
}
