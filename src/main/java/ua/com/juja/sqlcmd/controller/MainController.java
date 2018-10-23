package ua.com.juja.sqlcmd.controller;

import jdk.internal.util.xml.impl.Input;
import ua.com.juja.sqlcmd.controller.command.Command;
import ua.com.juja.sqlcmd.controller.command.*;
import ua.com.juja.sqlcmd.model.*;
import ua.com.juja.sqlcmd.view.Console;
import ua.com.juja.sqlcmd.view.View;


class MainController {
    private Command command;
    private View view = new Console();
    private DatabaseManager manager = new JDBCDatabaseManager();
    private InputWrapper inputSet = new InputWrapper();


    MainController() {
    }

    void run() {
        view.write("Hello user!");
        view.write("Please enter database, username and password in a format: connect|database|userName|password");
        while (true) {
            String input = view.read();
            InputWrapper.setInput(input);

          try{
                  String comm = InputWrapper.getCommand();
                  SqlCommand sqlCommand = SqlCommand.valueOf(comm);
                    switch (sqlCommand) {
                        case connect:
                            command = new Connect(manager);
                            break;

                        case insert:
                            command = new Insert(manager);
                            break;

                        case tables:
                            command = new Tables(manager);
                            break;

                        case drop:
                            command = new Drop(manager);
                            break;

                        case create:
                            command = new Create(manager);
                            break;

                        case clear:
                            command = new Clear(manager);
                            break;

                        case delete:
                            command = new Delete(manager);
                            break;
//
//                        case find:
//                            command = new Find(manager);
//                            break;

                        case update:
                            command = new Update(manager);
                            break;

                        case exit:
                            command = new Exit();
                            break;

                        case help:
                            command = new Help();
                            break;
                    }

                    view.write(command.getStatusProcess());

                } catch (IllegalArgumentException e) {
                   view.write("Not existing command "+ input);
                }
                catch (RuntimeException e){
                 view.write("You can not use this command until you have established connection to the database");
                }

                view.write("Please enter existing command or help");
            }
        }
    }





