package ua.com.juja.sqlcmd.model;


public interface DatabaseManager {

    DataSet[] getTableRows(String tableName);

    String[] getTableNames();

    void connect(String database, String userName, String password);

    void clear(String tableName);

    void create( DataSet column,String input);

    void update(String tableName, DataSet data, String id);

    String [] getColumnsNames(String tableName);

    void insert(String tableName, DataSet set, String key);

    void deleteTable(String tableName);

    void deleteRows(String tableName, String columnName, String rowName);

    boolean tableExist( String tableName);

    boolean isConnected();

}
