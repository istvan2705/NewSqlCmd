package ua.com.juja.sqlcmd.model;

import ua.com.juja.sqlcmd.controller.command.SqlCommand;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputSet {

    public String input;
    String PARSER = "(.*?\\|)(\\w+)(.*)";
    String SEPARATOR = "\\|";
    private Pattern pattern = Pattern.compile(PARSER);
    private Matcher matcher;
    private List<String> parameters;

    public SqlCommand getCommand(String input){
        SqlCommand command;
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            parameters = getParameters(matcher.group(1));
            command = SqlCommand.valueOf(parameters.get(0));

        } else {
            command = SqlCommand.valueOf(input);
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

    public List<String> getTableData(String input) {
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            parameters = getParameters(matcher.group(3).substring(1));
        }
        return parameters;
    }


    public Matcher getMatcher(String str, Pattern pattern) {
        matcher = pattern.matcher(str);
        return matcher;
    }

    public List<String> getParameters(String matcherGroup) {
        return Arrays.asList(matcherGroup.split(SEPARATOR));
    }



}



