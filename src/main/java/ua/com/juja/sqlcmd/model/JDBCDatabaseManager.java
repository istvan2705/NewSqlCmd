package ua.com.juja.sqlcmd.model;

import java.sql.*;
import java.util.*;


public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;


   @Override
    public void create(String tableName, DataSet columns) throws SQLException {
            try (Statement stmt = connection.createStatement()) {
                String columnNames = getColumnFormated(columns, "%s text NOT NULL, ");
                String sql = "CREATE TABLE IF NOT EXISTS public." + tableName + "(" + columnNames + ")";
                stmt.executeUpdate(sql);
            }
         }


    @Override
    public boolean tableExist(String tableName) throws SQLException {
        try {
            boolean tExists = false;
            try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
                while (rs.next()) {
                    String tName = rs.getString("TABLE_NAME");
                    if (tName != null && tName.equals(tableName)) {
                        tExists = true;
                        break;
                    }
                }
                return tExists;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
            ResultSet resultSet = metadata.getColumns(null, null, tableName, null)){

            while (resultSet.next()) {
                columns.add(resultSet.getString("COLUMN_NAME"));
            }
            return columns;
        }
    }
    @Override
    public List<DataSet> getTableRows(String tableName) throws SQLException {
        List<DataSet> set = new LinkedList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName)) {
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                set.add(dataSet);
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }

                return set;
            }
    }
    @Override
    public void clear(String tableName) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM public." + tableName);

        }
    }

    @Override
    public Set<String> getTableNames() throws SQLException{
       Set<String> columns = new LinkedHashSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'")) {

            while (rs.next()) {
                columns.add(rs.getString("table_name"));
            }

            return columns;

        }
    }

    @Override
    public void connect(String database, String user, String password) throws SQLException  {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
           throw new SQLException("Please add jdbc jar to project.", e);

        }
         connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/" + database, user,
           password);
        }


    public boolean isConnected() {
        return connection != null;

    }

    @Override
    public void insert(String tableName,DataSet set, String primaryKey) throws SQLException  {
        try (Statement stmt = connection.createStatement()) {
            String columns = getNameFormated(set, "%s,");
            String values = getValuesFormated(set, "'%s',");
           stmt.executeUpdate("INSERT INTO public." + tableName + "(" + columns + ")" +
                    "VALUES (" + values + ")" + " ON CONFLICT " + "(" + primaryKey + ")" + " DO NOTHING");
               }
        }

    @Override
    public void deleteRows(String tableName,String columnName, String rowName) throws SQLException  {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM public." + tableName + " WHERE " + columnName + " = ?")){
             ps.setString(1, rowName);
            ps.executeUpdate();
                    }
                }


    @Override
    public void update(String tableName, String id, DataSet set) throws SQLException  {
       String columns = getNameFormated(set, "%s = ?,");
        try (PreparedStatement ps = connection.prepareStatement("UPDATE public." + tableName + " SET " + columns + " WHERE id = ?")) {
            int index = 1;
            for (Object value : set.getValues()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setString(index, id);
            ps.executeUpdate();
        }
   }


    private String getNameFormated(DataSet name, String format) {
        String names = "";
        for (String newName : name.getNames()) {
            names += String.format(format, newName);
        }
        names = names.substring(0, names.length() - 1);
        return names;
    }

    private String getValuesFormated(DataSet input, String format) {
        String values = "";
        for (Object value : input.getValues()) {
            values += String.format(format, value);
        }
        values = values.substring(0, values.length() - 1);
        return values;
    }

    private String getColumnFormated(DataSet newValue, String format) {
        String names = "";
        for (String name : newValue.getNames()) {
            names += String.format(format, name);
        }
        names = names.substring(0, names.length() - 2);
        return names;

    }


}