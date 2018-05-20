package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DataClass {
    private DataSet set = new DataSet();

    private static List<String> getParameter(String parameter) {
        return Arrays.asList(parameter.split("\\|"));
    }

    String getNameIfTwoParameters(String input) {
        String tableName = null;
        Pattern upd = getPatternCompileWithTwoParameters();
        Matcher matcher = upd.matcher(input);
        if (matcher.find()) {
            tableName = matcher.group(2);
        }
        return tableName;
    }

    List<String> getDataTableIfTwoParameters(String input) {
        List<String> values = new ArrayList<>();
        Pattern upd = getPatternCompileWithTwoParameters();
        Matcher matcher = upd.matcher(input);
        if (matcher.find()) {
            values = getParameter(matcher.group(0));
        }
        return values;
    }

    private Pattern getPatternCompileWithTwoParameters() {
        return Pattern.compile("(.*?\\|)(.*)");
    }

    String getTableName(String input) {
        String tableName = null;
        Pattern upd = getPatternCompile();
        Matcher matcher = upd.matcher(input);
        if (matcher.find()) {
            List<String> name = getParameter(matcher.group(2));
            tableName = name.get(0);
        }
        return tableName;
    }

    List<String> getDataTable(String input) {
        List<String> values = new ArrayList<>();
        Pattern upd = getPatternCompile();
        Matcher matcher = upd.matcher(input);
        if (matcher.find()) {
            values = getParameter(matcher.group(3));
        }
        return values;
    }

    private Pattern getPatternCompile() {
        return Pattern.compile("(.*?\\|)(.*?\\|)(.*)");
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




