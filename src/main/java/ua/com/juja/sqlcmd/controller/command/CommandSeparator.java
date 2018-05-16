package ua.com.juja.sqlcmd.controller.command;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandSeparator {
    CommandsList command;

    public CommandSeparator(CommandsList command) {
        this.command = command;
    }

    public List<String> getSeparationResult(String input) {

        switch (command) {
            case INSERT:
                Pattern update = Pattern.compile("(insert\\|\\w*\\|)|(\\|)");
                return getTableData(input, update);

            case UPDATE:
                Pattern drop = Pattern.compile("(update\\|\\w*\\|)|(\\|)");
                return getTableData(input, drop);

            case DELETE:
                Pattern delete = Pattern.compile("(delete\\|\\w*\\|)|(\\|)");
                return getTableData(input, delete);

            case CREATE:
                Pattern create = Pattern.compile("(create\\|\\w*\\|)|(\\|)");
                return getTableData(input, create);

            default:
                return Arrays.asList(input.split("\\|"));
        }
    }

    private List<String> getTableData(String input, Pattern update) {
        return Arrays.asList(update.split(input));
    }
}
