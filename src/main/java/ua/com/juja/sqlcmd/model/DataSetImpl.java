package ua.com.juja.sqlcmd.model;


import java.util.*;

public class DataSetImpl implements DataSet {


    private HashMap<String, String> map = new LinkedHashMap<>();


    @Override
    public void put(String name, String value) {
        map.put(name, value);
    }

    @Override
    public List<String> getValues() {
        return new ArrayList<>(map.values());
    }

     @Override
    public List<String> getColumns() {
        int i = 0;
     InputSet inputSet = new InputSet();
        List<String> list = inputSet.getTableData();
        List<String> columns = new ArrayList<>();
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
        InputSet inputSet = new InputSet();
        int i = 0;
        List<String> list = inputSet.getTableData();
        List<String> values = new ArrayList<>();
        for (String value : list) {
            if (i % 2 != 0) {
                values.add(value);
            }
            i++;
        }
        return values;
    }
}
