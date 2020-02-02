package ua.com.juja.sqlcmd.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandParser {
    private static final Integer ZERO_INDEX = 0;
    private static final Integer FIRST_INDEX = 1;
    private static String SEPARATOR = "\\|";

    public String getCommandName(String input) {
        List<String> parameters = getParameters(input);
        return parameters.get(ZERO_INDEX);
    }

    public List<String> getParameters(String input) {
        return Arrays.asList(input.split(SEPARATOR));
    }

    public String getTableName(String input) {
        List<String> parameters = getParameters(input);
        return parameters.get(FIRST_INDEX);
    }

    public int getNumberOfParameters(String input) {
        List<String> parameters = Arrays.asList(input.split(SEPARATOR));
        return parameters.size();
    }

    public List<String> getColumns(String input) {
        List<String> columnsAndRows = getAllParameters(input);
        return getOneParameter(columnsAndRows, 0);
    }

    // to get list one of the two parameters: columns or rows
    private List<String> getOneParameter(List<String> columnsAndRows, int startIndex) {
        int size = columnsAndRows.size();
        int everySecondParameter = 2;
        int limit = size / everySecondParameter + Math.min(size % everySecondParameter, 1);
        return Stream.iterate(startIndex, i -> i + everySecondParameter)
                .limit(limit)
                .map(columnsAndRows::get)
                .collect(Collectors.toList());
    }

    // to get list of all parameters for table excluding command name and table name
    private List<String> getAllParameters(String input) {
        int firstTwoParameters = 2;
        List<String> allParameters = getParameters(input);
        return allParameters
                .stream()
                .skip(firstTwoParameters)
                .collect(Collectors.toList());
    }

    public List<String> getRows(String input) {
        List<String> columnsAndRows = getAllParameters(input);
        return getOneParameter(columnsAndRows, 1);
    }
}



