package ua.com.juja.sqlcmd.model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputWrapper {

    private static String input;
    private static String PARSER = "(.*?\\|)(\\w+)(.*)";
    private static String SEPARATOR = "\\|";
    private static List<String> parameters;
    private static HashMap<String, String> map = new LinkedHashMap<>();


    public static void setInput(String input) {
        InputWrapper.input = input;
    }

    public static List<String> getParametersFromInput() {
        parameters = Arrays.asList(input.split(SEPARATOR));
        return parameters;
    }

    public static String getCommand() {
        parameters = getParametersFromInput();
        return parameters.get(0);
    }

    public static String getTableName() {
        parameters = getParametersFromInput();
        return parameters.get(1);
    }

    public static List<String> getTableData() {
        Pattern pattern = Pattern.compile(PARSER);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String groupThird = matcher.group(3).substring(1);
            parameters = Arrays.asList(groupThird.split(SEPARATOR));
        }
        return parameters;
    }

    public static int getNumberOfParameters() {
        List<String> parameters = Arrays.asList(input.split(SEPARATOR));
        return parameters.size();
    }

    public static void put(String name, String value) {
        map.put(name, value);
    }

    public static List<String> getValues() {
        return new ArrayList<>(map.values());
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

    public static List<String> getRows() {
        int i = 0;
        List<String> list = InputWrapper.getTableData();
        List<String> values = new ArrayList<>();
        for (String value : list) {
            if (i % 2 != 0) {
                values.add(value);
            }
            i++;
        }
        return values;
    }
}



