package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlcmd.controller.command.SqlCommand;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputSet {

    public static String input;
    private String PARSER = "(.*?\\|)(\\w+)(.*)";
    private String SEPARATOR = "\\|";
    private Pattern pattern = Pattern.compile(PARSER);
    private Matcher matcher;
    private List<String> parameters;

    public void setInput(String input) {
        this.input = input;
    }

    public SqlCommand getCommand() {
        SqlCommand command;
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            String groupFirst = matcher.group(1);
            parameters = Arrays.asList(groupFirst.split(SEPARATOR));
            command = SqlCommand.valueOf(parameters.get(0));

        } else {
            command = SqlCommand.valueOf(input);
        }
        return command;
    }


     public String getTableName() {
        String tableName = null;
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            tableName = matcher.group(2);
        }
        return tableName;
    }

    public List<String> getTableData() {
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            String groupSecond = matcher.group(3).substring(1);
            parameters = Arrays.asList(groupSecond.split(SEPARATOR));
        }
        return parameters;
    }
    public Matcher getMatcher(String input, Pattern pattern) {
        matcher = pattern.matcher(input);
        return matcher;
    }

    public int getNumberOfParameters() {
        List<String> parameters = Arrays.asList(input.split(SEPARATOR));
        return parameters.size();
    }


}



