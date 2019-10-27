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
        int skip = 2;
        List<String> parameters = getParameters(input).stream().skip(skip).collect(Collectors.toList());
        int size = parameters.size();
        int limit = size / skip + Math.min(size % skip, 1);
        List<String> columns = Stream.iterate(0, i -> i + skip)
                .limit(limit)
                .map(parameters::get)
                .collect(Collectors.toList());
        return columns;
    }

    public List<String> getRows(String input) {
        int skip = 2;
        List<String> parameters = getParameters(input).stream().skip(skip).collect(Collectors.toList());
        ;
        int size = parameters.size();
        int limit = size / skip + Math.min(size % skip, 1);
        List<String> rows = Stream.iterate(1, i -> i + skip)
                .limit(limit)
                .map(parameters::get)
                .collect(Collectors.toList());
        return rows;
    }
}



