package ua.com.juja.sqlcmd.controller.command;
public class IsConnected implements Command {

       @Override
       public String getStatusProcess(){
        return "You can not use this command until you have established connection to the database";
    }
}