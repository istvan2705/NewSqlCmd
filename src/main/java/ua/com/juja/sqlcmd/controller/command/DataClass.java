package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DataClass {
    List<String> getNameIfTwoParameters(String input) {
        List<String> tableName = new ArrayList<>();
        Pattern upd = Pattern.compile("(.*?\\|)(.*)");
        Matcher matcher = upd.matcher(input);
        if (matcher.find()) {
            tableName = getParameter(matcher.group(2));
        }
        return tableName;
    }

    List<String> getName(String input) {
        List<String> tableName = new ArrayList<>();
        Pattern upd = getPatternCompile();
        Matcher matcher = upd.matcher(input);
        if (matcher.find()) {
           tableName = getParameter(matcher.group(2));
        }
        return tableName;
     }

    private Pattern getPatternCompile() {
        return Pattern.compile("(.*?\\|)(.*?\\|)(.*)");
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
      private static List<String> getParameter (String parameter){
       return Arrays.asList(parameter.split("\\|"));

        }

    private DataSet set = new DataSet();
    String getTableName(List<String> data){
        return data.get(0);
    }

    DataSet getDataSet(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            set.put(data.get(i), data.get(++i));
        }
        return set;
    }
    DataSet getColumns(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            set.put(data.get(i), i);
        }
        return set;
    }
}




