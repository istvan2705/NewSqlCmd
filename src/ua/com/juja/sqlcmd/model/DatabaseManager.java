package ua.com.juja.sqlcmd.model;


public interface DatabaseManager {

    DataSet[] getTableData(String tableName);

    String[] getTableNames();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    void create( String [] data,String input);

    void update(String tableName, String[] data, String id);

    String [] getColumnsNames(String tableName);

    void insert(String tableName, String[] data, String key);

    void deleteTable(String tableName);

    void deleteRows(String tableName, String columnName, String rowName);

    boolean tableExist( String tableName);

    boolean isConnected();

}
