package ua.com.juja.sqlcmd.model;


import java.sql.SQLException;

public interface DatabaseManager {

    String[] getTableData(String tableName, Integer limit, Integer offset) throws SQLException;

    void connect(String database, String userName, String password) throws SQLException;

    void clear(String tableName)  throws SQLException;

    void create(String tableName, DataSet columns) throws SQLException;

    void update(String tableName, String id, DataSet set) throws SQLException;

    String [] getTableNames() throws SQLException;

    void insert(String tableName,DataSet set, String primaryKey) throws SQLException;

    void deleteTable(String tableName) throws SQLException;

    void deleteRows(String tableName,String columnName, String rowName) throws SQLException;

    boolean tableExist( String tableName) throws SQLException;

    boolean isConnected();

}
