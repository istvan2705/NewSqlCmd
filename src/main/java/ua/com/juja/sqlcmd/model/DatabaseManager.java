package ua.com.juja.sqlcmd.model;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface DatabaseManager {

    void getTableRows(String tableName) throws SQLException;

    Set<String> getColumnsNames(String tableName) throws SQLException;

    void connect(String database, String userName, String password) throws SQLException;

    boolean clear(String tableName) throws SQLException;

    void create(String tableName, List<String> columns) throws SQLException;

    boolean update(String tableName, List<String> column, List<String> row, String keyColumn, String keyValue) throws SQLException;

    Set<String> getTableNames() throws SQLException;

    void insert(String tableName, List<String> columns, List<String> rows) throws SQLException;

    void deleteTable(String tableName) throws SQLException;

    boolean deleteRows(String tableName, String columnName, String rowName) throws SQLException;

    boolean isConnected();

    boolean isUpdateTable(PreparedStatement ps) throws SQLException;

    String getColumnFormatted(List<String> columns, String format);

    String getValuesFormatted(List<String> values, String format);
}
