package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.view.View;

public class Help implements Command {
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {

        view.write("Existing commands:");

        view.write("\tconnect|databaseName|username|password");
        view.write("\t\tto connect to database");

        view.write("\tcreate|tableName|column1|column2|...|columnN");
        view.write("\t\tto create table with columns");

        view.write("\tclear|tableName");
        view.write("\t\tto clear of table's content");

        view.write("\tdrop|tableName");
        view.write("\t\tto delete table");

        view.write("\tdelete|tableName|column|value");
        view.write("\t\tcommand deletes records for which the condition is satisfied column = value");

        view.write("\tinsert|tableName|column1|value1|column2|value2|columnN|valueN");
        view.write("\t\tto insert row into the table");

        view.write("\tupdate|tableName|column1|value1|column2|value2|columnN|valueN");
        view.write("\t\tcommand updates the record by setting the column value2 = the value2 for which the condition is satisfied column1 = value1");

        view.write("\tlist");
        view.write("\t\tto get list of tables");

        view.write("\tfind|tableName");
        view.write("\t\tto get content of table 'tableName'");

        view.write("\thelp");
        view.write("\t\tto display list of command");

        view.write("\texit");
        view.write("\t\tto exit the program");
    }
}
