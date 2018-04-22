package ua.com.juja.sqlcmd.model;


import java.sql.SQLException;

public interface DatabaseManager {

    DataSet[] getTableRows(String tableName);

    String[] getTableNames();

    void connect(String database, String userName, String password) throws SQLException;

    void clear(String command) throws SQLException;

    void create(String command) throws SQLException;

    void update(String command) throws SQLException;

    String [] getColumnsNames(String tableName);

    void insert(String tableName, DataSet set, String key);

    void deleteTable(String tableName) throws SQLException;

    void deleteRows(String tableName, String columnName, String rowName);

    boolean tableExist( String tableName);

    boolean isConnected();

}
