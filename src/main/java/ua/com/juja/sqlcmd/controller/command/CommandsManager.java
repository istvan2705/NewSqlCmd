package ua.com.juja.sqlcmd.controller.command;

import java.util.List;

public class CommandsManager {
    private List<Command> commands;

    public CommandsManager(List<Command> commands) {
        this.commands = commands;
    }

    public void doCommand(String input) {
        for (Command command : commands) {

            if (command.canProcess(input)) {
                command.process(input);
                break;
            }
        }
    }
}