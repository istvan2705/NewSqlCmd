package ua.com.juja.sqlcmd.controller.command;

public class Exit implements Command {

    public Exit() {
    }

    @Override
    public String getStatusProcess() {
            return "See you soon!";
        }
    }
