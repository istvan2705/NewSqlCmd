package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.view.View;

public class DeleteContent implements Command {
    private DatabaseManager manager;
    private View view;

    public DeleteContent(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        try{   String[] data = command.split("\\|");
            String tableName = data[1];
            manager.clear(tableName);

        }
        catch(
                ArrayIndexOutOfBoundsException e){
            view.write("You did not enter any name of table. Please enter it.");
        }
    }
}