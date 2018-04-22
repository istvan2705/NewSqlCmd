package ua.com.juja.sqlcmd.model;


import java.sql.SQLException;

public interface DatabaseManager {

    String[] getTableData(String command) throws SQLException;

    void connect(String database, String userName, String password) throws SQLException;

    void clear(String command) throws SQLException;

    void create(String command) throws SQLException;

    void update(String command) throws SQLException;

    String [] getTableNames() throws SQLException;

    void insert(String command) throws SQLException;

    void deleteTable(String tableName) throws SQLException;

    void deleteRows(String command) throws SQLException;

    boolean tableExist( String tableName);

    boolean isConnected();

}
