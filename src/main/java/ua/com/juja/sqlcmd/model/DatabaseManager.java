package ua.com.juja.sqlcmd.model;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatabaseManager {

     List<DataSet> getTableRows(String tableName) throws SQLException;

    Set<String> getColumnsNames(String tableName) throws SQLException;

    void connect(String database, String userName, String password) throws SQLException;

    boolean clear(String tableName)  throws SQLException;

    boolean create(String tableName, DataSet columns) throws SQLException;

    boolean update(String tableName, String id, DataSet set) throws SQLException;

    Set<String> getTableNames() throws SQLException;

    boolean insert(String tableName,DataSet set, String primaryKey) throws SQLException;

    void deleteTable(String tableName) throws SQLException;

    boolean deleteRows(String tableName,String columnName, String rowName) throws SQLException;

    boolean isConnected();

    boolean isUpdateTable(PreparedStatement ps) throws SQLException;

    String getNameFormatted(DataSet name, String format);

    String getValuesFormatted(DataSet input, String format);


}
