package ua.com.juja.sqlcmd.controller.command;
import java.util.List;

public class CommandGenerator<Command> {
    SqlCommand command;
    private List<String> tableName;
    private List<String> values;

    public CommandGenerator(SqlCommand command, List<String> tableName, List<String> values) {
        this.command = command;
        this.tableName = tableName;
        this.values = values;
    }
}
