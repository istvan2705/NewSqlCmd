package ua.com.juja.sqlcmd.model;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataSet {

    private static final String PARSER = "(.*?\\|)(\\w+)(.*)";
    private static final String SEPARATOR = "\\|";
    private Pattern pattern = Pattern.compile(PARSER);
    private Map<String, String> data = new LinkedHashMap<>();
    private Matcher matcher;
    private List<String> parameters;

    public void put(String name, String value) {
        data.put(name, value);
    }

    public List<String> getValues() {
        return new ArrayList<>(data.values());
    }

    public String getCommand(String input) {
        String command;
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            parameters = getParameters(matcher.group(1));
            command = parameters.get(0);
        } else {
            command = input;
        }
        return command;
    }

    public String getTableName(String input) {
        String tableName = null;
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            tableName = matcher.group(2);
        }
        return tableName;
    }

    public List<String> getDataTable(String input) {
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            parameters = getParameters(matcher.group(3).substring(1));
        }
        return parameters;
    }

    public Map<String, String> setValuesToColumns(List<String> tableData) {
        for (int i = 0; i < tableData.size(); i++) {
            data.put(tableData.get(i), tableData.get(++i));
        }
        return data;
    }

    public List<String> getParameters(String input) {
        return Arrays.asList(input.split(SEPARATOR));
    }

    private Matcher getMatcher(String str, Pattern pattern) {
        matcher = pattern.matcher(str);
        return matcher;
    }
}
