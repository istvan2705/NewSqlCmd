package ua.com.juja.sqlcmd.model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputWrapper {

    private static String input;
    private static String PARSER = "(.*?\\|)(\\w+)(.*)";
    private static String SEPARATOR = "\\|";
    private static List<String> parameters;

    public static void setInput(String input) {
        InputWrapper.input = input;
    }



    public static String getCommand() {
        parameters = getParametersFromInput();
        return parameters.get(0);
    }

    private static List<String> getParametersFromInput() {
         return Arrays.asList(input.split(SEPARATOR));
    }

    public static String getTableName() {
        parameters = getParametersFromInput();
        return parameters.get(1);
    }

    public static List<String> getTableData() {
        Pattern pattern = Pattern.compile(PARSER);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String tableData = matcher.group(3).substring(1);
            parameters = Arrays.asList(tableData.split(SEPARATOR));
        }
        return parameters;
    }

    public static int getNumberOfParameters() {
        List<String> parameters = Arrays.asList(input.split(SEPARATOR));
        return parameters.size();
    }

    public static List<String> getColumns() {
        int i = 0;
        List<String> list = InputWrapper.getTableData();
        List<String> columns = new ArrayList<>();
        for (String column : list) {
            if (i % 2 == 0) {
                columns.add(column);
            }
            i++;
        }
        return columns;
    }

    public static List<Object> getRows() {
        int i = 0;
        List<String> list = InputWrapper.getTableData();
        List<Object> values = new ArrayList<>();
        for (Object value : list) {
            if (i % 2 != 0) {
                values.add(value);
            }
            i++;
        }
        return values;
    }
}



