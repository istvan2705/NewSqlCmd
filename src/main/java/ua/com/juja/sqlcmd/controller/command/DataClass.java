package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DataClass {

    private static final String PARSER = "(.*?\\|)(.*?\\|)(.*)";
    private static final String PARSER_TWO_PARAMETERS = "(.*?\\|)(.*)";
    private static final String SEPARATOR = "\\|";
    private DataSet set = new DataSet();


    String getTableName(String input) {
        String tableName = null;
        Pattern upd;
        Matcher matcher;
        if (getParameters(input).size() > 2) {
            upd = getPattern();
            matcher = upd.matcher(input);
            if (matcher.find()) {
                List<String> name = getParameters(matcher.group(2));
                tableName = name.get(0);
            }
        } else {
            upd = getPatternTwoParameters();
            matcher = upd.matcher(input);
            if (matcher.find()) {
                tableName = matcher.group(2);
            }
        }
        return tableName;
    }

    List<String> getDataTable(String input) {
        List<String> values = new ArrayList<>();
        Pattern upd;
        Matcher matcher;
        if (getParameters(input).size() > 2) {
            upd = getPattern();
            matcher = upd.matcher(input);
            if (matcher.find()) {
                values = getParameters(matcher.group(3));
            }
        } else {
            upd = getPatternTwoParameters();
            matcher = upd.matcher(input);
            if (matcher.find()) {
                values = getParameters(matcher.group(0));
            }
        }
        return values;
    }

    private static List<String> getParameters(String input) {
        return Arrays.asList(input.split(SEPARATOR));
    }

    private Pattern getPattern() {
        return Pattern.compile(PARSER);
    }

    private Pattern getPatternTwoParameters() {
        return Pattern.compile(PARSER_TWO_PARAMETERS);
    }

    DataSet setValuesToColumns(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            set.put(data.get(i), data.get(++i));
        }
        return set;
    }

    DataSet setColumns(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            set.put(data.get(i), i);
        }
        return set;
    }
}




