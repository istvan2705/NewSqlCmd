package ua.com.juja.sqlcmd.model;

import java.sql.*;
import java.util.Arrays;


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
    public String[] getTableData(String tableName, Integer limit, Integer offset) throws SQLException {

        String[] result = null;
        if (limit == null) {
            result = new String[1000];
        } else {
            result = new String[limit];
        }

        try (Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            String limitOffsetSubQuery = "";
            if (limit != null) {
                limitOffsetSubQuery = " LIMIT " + limit + " OFFSET " + offset;
            }

            ResultSet resultSet = stmt.executeQuery("SELECT * FROM public." + tableName + limitOffsetSubQuery);
            int columnsCount = resultSet.getMetaData().getColumnCount();

            result[0] = toCSV(getColumnNames(resultSet));

            int rowIndex = 1;
            while (resultSet.next() && rowIndex < 1000) {
                String[] rowData = new String[columnsCount];
                for (int columnIndex = 0; columnIndex < columnsCount; columnIndex++) {
                    rowData[columnIndex] = resultSet.getString(columnIndex + 1);
                }

                result[rowIndex] = toCSV(rowData);
                rowIndex++;
            }
            result = Arrays.copyOf(result, rowIndex);

            resultSet.close();
        }
        return result;
    }

    private String toCSV(String[] array) {
        StringBuilder buffer = new StringBuilder();
        for (String element : array) {
            buffer.append(element).append(',');
        }
        String result = buffer.toString();
        return result.substring(0, result.length() - 1);
    }

    private String[] getColumnNames(ResultSet resultSet) throws SQLException {
        String[] result = new String[resultSet.getMetaData().getColumnCount()];
        for (int index = 0; index < result.length; index++) {
            result[index] = resultSet.getMetaData().getColumnName(index + 1);
        }
        return result;
    }

    @Override
    public void clear(String tableName) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM public." + tableName);

        }
    }

    public String[] getTableNames() throws SQLException{
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'")) {
            String[] tables = new String[100]; //TODO magic number
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("table_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            return tables;

        }
    }

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

    public void insert(String tableName,DataSet set, String primaryKey) throws SQLException  {


        try (Statement stmt = connection.createStatement()) {
            String columns = getNameFormated(set, "%s,");
            String values = getValuesFormated(set, "'%s',");
           stmt.executeUpdate("INSERT INTO public." + tableName + "(" + columns + ")" +
                    "VALUES (" + values + ")" + " ON CONFLICT " + "(" + primaryKey + ")" + " DO NOTHING");
               }
        }

    public void deleteRows(String tableName,String columnName, String rowName) throws SQLException  {

        try (Statement stmt = connection.createStatement()){
             PreparedStatement ps = connection.prepareStatement("DELETE FROM public." + tableName + " WHERE " + columnName + " = ?");
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