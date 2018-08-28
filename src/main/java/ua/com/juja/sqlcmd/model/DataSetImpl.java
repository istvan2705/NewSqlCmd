package ua.com.juja.sqlcmd.model;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataSetImpl implements DataSet{

    private static final String PARSER = "(.*?\\|)(\\w+)(.*)";
    private static final String SEPARATOR = "\\|";
    private Pattern pattern = Pattern.compile(PARSER);
    private Matcher matcher;
    private Map<String, String> data = new LinkedHashMap<>();
    private List<String> parameters;

    @Override
    public void put(String name, String value) {
        data.put(name, value);
    }

    @Override
    public List<String> getValues() {
        return new ArrayList<>(data.values());
    }

    @Override
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

    @Override
    public String getTableName(String input) {
        String tableName = null;
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            tableName = matcher.group(2);
        }
        return tableName;
    }

    @Override
    public List<String> getTableData(String input) {
        matcher = getMatcher(input, pattern);
        if (matcher.find()) {
            parameters = getParameters(matcher.group(3).substring(1));
        }
        return parameters;
    }

    @Override
   public Matcher getMatcher(String str, Pattern pattern) {
        matcher = pattern.matcher(str);
        return matcher;
    }

    @Override
    public List<String> getParameters(String matcherGroup) {
        return Arrays.asList(matcherGroup.split(SEPARATOR));
    }

    @Override
    public Map<String, String> setValuesToColumns(List<String> tableData) {
        for (int i = 0; i < tableData.size(); i++) {
            data.put(tableData.get(i), tableData.get(++i));
        }
        return data;
    }

    @Override
    public Map<String, String> setUpdatedValuesToColumns(List<String> values) {
        Map<String, String> set = setValuesToColumns(values);
        Object firstSet = set.keySet().toArray()[0];
        set.remove(firstSet);
        return set;
    }


}
