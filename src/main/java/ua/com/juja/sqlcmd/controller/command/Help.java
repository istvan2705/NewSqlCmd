package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.InputWrapper;

public class Help implements Command {

    public Help() {
    }

    @Override
    public String getStatusProcess() {
        int numberOfParameters = InputWrapper.getNumberOfParameters();
        if (numberOfParameters != 1) {
            return ERROR_ENTERING_MESSAGE + "'tables'";
        }

        return "Existing commands:" + "\n" +

                "\tconnect|databaseName|username|password" + "\n" +
                "\t\tto connect to database" + "\n" +

                "\tcreate|tableName|column1|column2|...|columnN" + "\n" +
                "\t\tto create table with columns" + "\n" +

                "\tclear|tableName" + "\n" +
                "\t\tto clear of table's content" + "\n" +

                "\tdrop|tableName" + "\n" +
                "\t\tto delete table" + "\n" +

                "\tdelete|tableName|column|value" + "\n" +
                "\t\tDatabaseManagerMockitoTest deletes records for which the condition is satisfied column = value" + "\n" +

                "\tinsert|tableName|column1|value1|column2|value2|columnN|valueN" + "\n" +
                "\t\tto insert row into the table" + "\n" +

                "\tupdate|tableName|column1|value1|column2|value2|columnN|valueN" + "\n" +

                "\t\tDatabaseManagerMockitoTest updates the record by setting the column value2 = the value2 for which the condition" + "\n" +
                "\t\tis satisfied column1 = value1" + "\n" +

                "\tlist" + "\n" +
                "\t\tto get list of tables" + "\n" +

                "\tfind|tableName" + "\n" +
                "\t\tto get content of table 'tableName'" + "\n" +

                "\thelp" + "\n" +
                "\t\tto display list of DatabaseManagerMockitoTest" + "\n" +

                "\texit" + "\n" +
                "\t\tto exit the program";
    }
}
