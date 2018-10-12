package ua.com.juja.sqlcmd.model;


import ua.com.juja.sqlcmd.controller.command.SqlCommand;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface DataSet {
    String PARSER = "(.*?\\|)(\\w+)(.*)";
    String SEPARATOR = "\\|";

    void put(String name, String value);

    List<String> getValues();

    void setInput(String input);

    SqlCommand getCommand();

    String getTableName();

    List<String> getTableData();

    Matcher getMatcher(String str, Pattern pattern);

    List<String> getParameters();

    Map<String, String> getValuesForColumns(List<String> tableData);

    Map<String, String> getUpdatedValuesForColumns(List<String> values);

}
