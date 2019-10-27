package ua.com.juja.sqlcmd.controller;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class JDBCDatabaseManager implements DatabaseManager {

    private static Connection connection;

    @Override
    public void create(String tableName, List<String> columns) throws SQLException {
        String columnNames = getColumnFormatted(columns, "%s text,");
        String sql = "CREATE TABLE public." + tableName + "(" + columnNames + ")";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteTable(String tableName) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE public." + tableName);
        }
    }

    @Override
    public Set<String> getColumnsNames(String tableName) throws SQLException {
        Set<String> columns = new LinkedHashSet<>();
        DatabaseMetaData metadata = connection.getMetaData();
        try (
                ResultSet resultSet = metadata.getColumns(null, null, tableName, null)) {
            while (resultSet.next()) {
                columns.add(resultSet.getString("COLUMN_NAME"));
            }
            return columns;
        }
    }

    @Override
    public List<String> getTableRows(String tableName) throws SQLException {
        List<String> rows = new LinkedList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    rows.add(rs.getString(i));
                }
                rows.add("\n");
            }
            return rows;
        }
    }

    @Override
    public boolean clear(String tableName) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM public." + tableName)) {
            return isUpdateTable(ps);
        }
    }

    @Override
    public boolean isUpdateTable(PreparedStatement ps) throws SQLException {
        return ps.executeUpdate() > 0;
    }

    @Override
    public Set<String> getTableNames() throws SQLException {
        Set<String> tables = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'")) {
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
            return tables;
        }
    }

    @Override
    public void connect(String database, String user, String password) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Please add jdbc jar to project.", e);
        }
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/" + database, user,
                password);
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public void insert(String tableName, List<String> column, List<String> row) throws SQLException {
        String columns = getColumnFormatted(column, "%s,");
        String values = getValuesFormatted(row, "'%s',");
        String insertData = "INSERT INTO public." + tableName + "(" + columns + ")" +
                "VALUES (" + values + ")";
        try (PreparedStatement ps = connection.prepareStatement(insertData)) {
            ps.executeUpdate();
        }
    }

    @Override
    public boolean deleteRows(String tableName, String columnName, String rowName) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM public." + tableName + " WHERE " + columnName + " = ?")) {
            ps.setString(1, rowName);
            return isUpdateTable(ps);
        }
    }

    @Override
    public boolean update(String tableName, String keyColumn, String keyValue, String column, String value) throws SQLException {
        String formattedValue = String.format("'%s'", value);
        try (PreparedStatement ps = connection.prepareStatement("UPDATE public." + tableName + " SET " + column + " = " + formattedValue + " WHERE " + keyColumn + " = ?")) {
            int index = 1;
            ps.setString(index, keyValue);
            ps.executeUpdate();
            return isUpdateTable(ps);
        }
    }

    @Override
    public String getColumnFormatted(List<String> columns, String format) {
        StringBuilder names = new StringBuilder();
        for (String columnName : columns) {
            names.append(String.format(format, columnName));
        }
        return names.toString().substring(0, names.length() - 1);
    }

    @Override
    public String getValuesFormatted(List<String> values, String format) {
        StringBuilder names = new StringBuilder();
        for (String value : values) {
            names.append(String.format(format, value));
        }
        return names.toString().substring(0, names.length() - 1);
    }
}