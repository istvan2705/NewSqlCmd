package ua.com.juja.sqlcmd.model;


import ua.com.juja.sqlcmd.controller.command.SqlCommand;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataSetImpl implements DataSet {

    private String input;
    private Pattern pattern = Pattern.compile(PARSER);
    private Matcher matcher;
    private HashMap<String, String> map = new LinkedHashMap<>();
    private List<String> parameters;

    @Override
    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public void put(String name, String value) {
        map.put(name, value);
    }

    @Override
    public List<String> getValues() {
        return new ArrayList<>(map.values());
    }

    @Override
    public SqlCommand getCommand() {
        SqlCommand command;
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            String matcherGroup = matcher.group(1);
            parameters = Arrays.asList(matcherGroup.split(SEPARATOR));
            command = SqlCommand.valueOf(parameters.get(0));

        } else {
            command = SqlCommand.valueOf(input);
        }
        return command;
    }

    @Override
    public String getTableName() {
        String tableName = null;
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            tableName = matcher.group(2);
        }
        return tableName;
    }

    @Override
    public List<String> getTableData() {
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            String matcherGroup = matcher.group(3).substring(1);
            parameters = Arrays.asList(matcherGroup.split(SEPARATOR));
        }
        return parameters;
    }

    @Override
    public Matcher getMatcher(String input, Pattern pattern) {
        matcher = pattern.matcher(input);
        return matcher;
    }


    @Override
    public List<String> getParameters() {
        return Arrays.asList(input.split(SEPARATOR));
    }

    @Override
    public List<String> getColumns() {
        int i = 0;
        List<String> columns = new ArrayList<>();
        List<String> list = getTableData();
        for (String column : list) {
            if (i % 2 == 0) {
                columns.add(column);
            }
            i++;
        }
        return columns;
    }

    @Override
    public List<String> getRows() {
        int i = 0;
        List<String> values = new ArrayList<>();
        List<String> list = getTableData();
        for (String value : list) {
            if (i % 2 != 0) {
                values.add(value);
            }
            i++;
        }
        return values;
    }

}
