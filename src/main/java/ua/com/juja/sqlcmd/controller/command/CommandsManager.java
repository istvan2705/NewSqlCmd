package ua.com.juja.sqlcmd.controller.command;

import java.util.List;

public class CommandsManager {
    private List<Command> commands;

    public CommandsManager(List<Command> commands) {
        this.commands = commands;
    }

    public void result(String command) {
        for (Command next : commands) {

            if (next.canProcess(command)) {
                next.process(command);
                break;
            }
        }
    }
}