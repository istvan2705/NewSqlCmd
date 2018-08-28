package ua.com.juja.sqlcmd.model;


import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface DataSet {
      String PARSER = "(.*?\\|)(\\w+)(.*)";
      String SEPARATOR = "\\|";


     void put(String name, String value);

     List<String> getValues();

     String getCommand(String input);

     String getTableName(String input);

     List<String> getTableData(String input);

     Matcher getMatcher(String str, Pattern pattern);

     List<String> getParameters(String matcherGroup);

     Map<String, String> setValuesToColumns(List<String> tableData);

     Map<String, String> setUpdatedValuesToColumns(List<String> values);

}
