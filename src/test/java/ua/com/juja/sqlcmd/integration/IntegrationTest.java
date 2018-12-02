package ua.com.juja.sqlcmd.integration;

import org.junit.*;
import ua.com.juja.sqlcmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @Before
    public void setup() {
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    private String getData() {
        String result = new String(out.toByteArray(), StandardCharsets.UTF_8).replaceAll("\n", "\n");
        out.reset();
        return result;
    }

    @Test
    public void testConnect() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        //connect|Academy|postgres|1401198n
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());
    }

    @Test
    public void testCreate() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("create|tvboxes|id|model");
        in.add("drop|tvboxes");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        // create|tvboxes|id|model
                        "The table 'tvboxes' has been created\n" +
                        "Please enter existing command or help\n" +
                        //drop|tvboxes
                        "The table 'tvboxes' has been deleted\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());
    }


    @Test
    public void testHelp() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("help");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals("Hello user!\n" +
                "Please enter database, username and password in a format: connect|database|userName|password\n" +
                // help
                "You have connected to database 'Academy' successfully!\n" +
                "Please enter existing command or help\n" +
                "Existing commands:\n" +
                "\tconnect|databaseName|username|password\n" +
                "\t\tto connect to database\n" +

                "\tcreate|tableName|column1|column2|...|columnN\n" +
                "\t\tto create table with columns\n" +

                "\tclear|tableName\n" +
                "\t\tto clear of table's content\n" +

                "\tdrop|tableName\n" +
                "\t\tto delete table\n" +

                "\tdelete|tableName|column|value\n" +
                "\t\tcommand deletes records for which the condition is satisfied column = value\n" +

                "\tinsert|tableName|column1|value1|column2|value2|columnN|valueN\n" +
                "\t\tto insert row into the table\n" +

                "\tupdate|tableName|column1|value1|column2|value2|columnN|valueN\n" +
                "\t\tcommand updates the record by setting the column value2 = the value2 for which the condition" + "\n" +
                "\t\tis satisfied column1 = value1" + "\n" +

                "\tlist\n" +
                "\t\tto get list of tables\n" +

                "\tfind|tableName\n" +
                "\t\tto get content of table 'tableName'\n" +

                "\thelp\n" +
                "\t\tto display list of command\n" +

                //exit
                "\texit\n" +
                "\t\tto exit the program\n" +
                "Please enter existing command or help\n" +
                "See you soon!\n", getData());

    }

    @Test
    public void testExit() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        //exit
                        "See you soon!\n", getData());
    }

    @Test
    public void testFind() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("find|tvsets");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // find|user
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        "--------------------------\n" +
                        "|id|model|date|price\n" +
                        "--------------------------\n" +
                        "|1|12|oct12|45|\n" +
                        "|2|x96|oct15|50|\n" +
                        "--------------------------\n" +
                        "Please enter existing command or help\n" +
                        //exit
                        "See you soon!\n", getData());
    }


    @Test
    public void testTables() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("tables");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        // tables
                        "[tvsets, teachers]\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());

    }

    @Test
    public void testInsert() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("insert|teachers|id|2|surname|Sidorov|name|Sergei");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        // insert|teachers|id|2|surname|Sidorov|name|Sergei
                        "Statement are added into the table 'teachers'\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());
    }

    @Test
    public void testDrop() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("create|dvdplayers|id|model|marka");
        in.add("drop|dvdplayers");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        // drop|dvdplayers
                        "The table 'dvdplayers' has been created\n" +
                        "Please enter existing command or help\n" +
                        "The table 'dvdplayers' has been deleted\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());
    }

    @Test
    public void testDelete() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("insert|teachers|id|4|surname|Ivanov|name|Ivan");
        in.add("delete|teachers|id|4");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        //insert|teachers|id|4|surname|Ivanov|name|Ivan
                        "Statement are added into the table 'teachers'\n" +
                        "Please enter existing command or help\n" +
                        // delete|dteachers|id|4
                        "The row has been deleted\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());
    }

    @Test
    public void testClear() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("create|students|id|surname");
        in.add("drop|students");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        //create|students|id|surname
                        "The table 'students' has been created\n" +
                        "Please enter existing command or help\n" +
                        //drop|teachers
                        "The table 'students' has been deleted\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());
    }


    @Test
    public void testUpdate() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("update|teachers|id|3|surname|Pupkin");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        // update|teachers|id|2|surname|Pupkin
                        "The row has been updated\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());

    }


    @Test
    public void testConnectWithError() {
        // given
        in.add("connect|Academys|postgres|1401198n");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "The connection to database 'Academys' for user 'postgres' is failed due to'FATAL: database \"Academys\" does not exist'\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());
    }


    @Test
    public void testClearWithError() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("clear|teachers|id|2");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then

        assertEquals(
                // connect
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        // clear|teachers|id|2
                        "Error entering command, it should be'clear|tableName'\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());
    }

    @Test
    public void testCreateWithErrors() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("create|students");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n" +
                        "You have connected to database 'Academy' successfully!\n" +
                        "Please enter existing command or help\n" +
                        // create|students
                        "Error entering command, it should be'create|tableName|column1|column2|...|columnN'\n" +
                        "Please enter existing command or help\n" +
                        // exit
                        "See you soon!\n", getData());
    }
}
