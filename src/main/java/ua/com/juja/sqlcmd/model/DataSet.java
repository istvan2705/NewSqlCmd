package ua.com.juja.sqlcmd.model;

import java.util.List;

public interface DataSet {

    void put(String name, String value);

    List<String> getValues();

    List<String> getColumns();

    List<String> getRows();



}
