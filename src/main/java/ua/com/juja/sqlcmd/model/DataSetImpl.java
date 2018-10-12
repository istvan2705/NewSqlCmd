package ua.com.juja.sqlcmd.model;


import ua.com.juja.sqlcmd.controller.command.SqlCommand;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataSetImpl implements DataSet {

    public String input;
    private Pattern pattern = Pattern.compile(PARSER);
    private Matcher matcher;
    private Map<String, String> map = new LinkedHashMap<>();
    private List<String> parameters;
    @Override
    public void setInput(String input){
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
    public SqlCommand getCommand(){
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
            public String getTableName (){
                String tableName = null;
                matcher = getMatcher(input, pattern);
                if (matcher.find()) {
                    tableName = matcher.group(2);
                }
                return tableName;
            }

            @Override
            public List<String> getTableData (){
               matcher = getMatcher(input, pattern);
                if (matcher.find()) {
                    String matcherGroup = matcher.group(3).substring(1);
                    parameters = Arrays.asList(matcherGroup.split(SEPARATOR));
                }
                return parameters;
            }

            @Override
            public Matcher getMatcher (String input, Pattern pattern){
                matcher = pattern.matcher(input);
                return matcher;
            }


            @Override
            public List<String> getParameters (){
                return Arrays.asList(input.split(SEPARATOR));
            }

            @Override
            public Map<String, String> getValuesForColumns(List <String> tableData) {
                for (int i = 0; i < tableData.size(); i++) {
                    map.put(tableData.get(i), tableData.get(++i));
                }
                return map;
            }

            @Override
            public Map<String, String> getUpdatedValuesForColumns(List <String> values) {
                Map<String, String> set = getValuesForColumns(values);
                Object firstSet = set.keySet().toArray()[0];
                set.remove(firstSet);
                return set;
            }
        }
