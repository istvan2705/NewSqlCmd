package ua.com.juja.sqlcmd.controller.command;

import ua.com.juja.sqlcmd.view.View;

public class Unsupported implements Command {

    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public void process(String command) {
        view.write("Not existing command " + command);
    }
}