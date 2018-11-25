package ua.com.juja.sqlcmd.integration;

import org.junit.*;
import ua.com.juja.sqlcmd.controller.Main;
import ua.com.juja.sqlcmd.model.DatabaseManager;
import ua.com.juja.sqlcmd.model.JDBCDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    private DatabaseManager manager;
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @Before
    public void setup() {
        manager = new JDBCDatabaseManager();
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
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
                "You have connected to database 'Academy' successfully!\n"+
                "Please enter existing command or help\n"+
                "Existing commands:\n"+
        "\tconnect|databaseName|username|password\n"+
        "\t\tto connect to database\n"+

        "\tcreate|tableName|column1|column2|...|columnN\n"+
        "\t\tto create table with columns\n"+

        "\tclear|tableName\n"+
        "\t\tto clear of table's content\n"+

        "\tdrop|tableName\n"+
        "\t\tto delete table\n"+

        "\tdelete|tableName|column|value\n"+
        "\t\tcommand deletes records for which the condition is satisfied column = value\n"+

       "\tinsert|tableName|column1|value1|column2|value2|columnN|valueN\n"+
        "\t\tto insert row into the table\n"+

       "\tupdate|tableName|column1|value1|column2|value2|columnN|valueN\n"+
         "\t\tcommand updates the record by setting the column value2 = the value2 for which the condition" + "\n" +
          "\t\tis satisfied column1 = value1" + "\n" +

        "\tlist\n"+
        "\t\tto get list of tables\n"+

        "\tfind|tableName\n"+
        "\t\tto get content of table 'tableName'\n"+

        "\thelp\n"+
        "\t\tto display list of command\n"+

       "\texit\n"+
        "\t\tto exit the program\n"
                //exit
                , getData());
    }

    private String getData() {
        String result = new String(out.toByteArray(), StandardCharsets.UTF_8).replaceAll("\n", "\n");
        out.reset();
        return result;
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
                "Please enter existing command or help\n", getData());

    }

    @Test
    public void testTablesWithoutConnect() {
        // given

        in.add("tables");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // tables
                "Hello user!\n" +
                "Please enter database, username and password in a format: connect|database|userName|password\n"+
                "You can not use this command until you have established connection to the database\n" +
                 "Please enter existing command or help\n", getData());
                // exit

    }

    @Test
    public void testFindWithoutConnect() {
        // given
        in.add("find|teachers");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // find|user
                "Hello user!\n" +
                        "Please enter database, username and password in a format: connect|database|userName|password\n"+
                        "You can not use this command until you have established connection to the database\n" +
                        "Please enter existing command or help\n", getData());
                        // exit

    }


    @Test
    public void testTablesAfterConnect() {
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
                "[tvsets, dvdplayers, teachers]\n" +
                "Please enter existing command or help\n", getData());

                // exit

    }

    @Test
    public void testFindAfterConnect() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("find|teachers");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                "+-----+--------+--+\n" +
                "|name |password|id|\n" +
                "+-----+--------+--+\n" +
                "|Vasia|****    |22|\n" +
                "+-----+--------+--+\n" +
                "Введи команду (или help для помощи):\n" +
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                "До скорой встречи!\n", getData());
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
                "Неудача! по причине: Формат команды 'connect|databaseName|userName|password', а ты ввел: connect|sqlcmd5hope5never5exist\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testFindAfterConnect_withData() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("clear|teachers");
        in.add("insert|teachers|id|3|surname|Ivanec|name|Ivan|subject|Physics");
        in.add("find|users");
        in.add("clear|users");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // clear|users
                "Таблица users была успешно очищена.\n" +
                "Введи команду (или help для помощи):\n" +
                // insert|users|id|13|name|Stiven|password|*****
                "В таблице 'users' была успешно добавлена запись:\n" +
                "+--+------+--------+\n" +
                "|id|name  |password|\n" +
                "+--+------+--------+\n" +
                "|13|Stiven|*****   |\n" +
                "+--+------+--------+\n" +
                "Введи команду (или help для помощи):\n" +
                // insert|users|id|14|name|Eva|password|+++++
                "В таблице 'users' была успешно добавлена запись:\n" +
                "+--+----+--------+\n" +
                "|id|name|password|\n" +
                "+--+----+--------+\n" +
                "|14|Eva |+++++   |\n" +
                "+--+----+--------+\n" +
                "Введи команду (или help для помощи):\n" +
                // find|users
                "+------+--------+--+\n" +
                "|name  |password|id|\n" +
                "+------+--------+--+\n" +
                "|Stiven|*****   |13|\n" +
                "+------+--------+--+\n" +
                "|Eva   |+++++   |14|\n" +
                "+------+--------+--+\n" +
                "Введи команду (или help для помощи):\n" +
                "Таблица users была успешно очищена.\n" +
                "Введи команду (или help для помощи):\n" +
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testClearWithError() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("clear|sadfasd|fsf|fdsf");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then

        assertEquals(
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // clear|sadfasd|fsf|fdsf
                "Неудача! по причине: Формат команды 'clear|tableName', а ты ввел: clear|sadfasd|fsf|fdsf\n" +
                "Введи команду (или help для помощи):\n" +
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testCreateWithErrors() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("insert|user|error");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                // connect
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // insert|user|error
                "Неудача! по причине: Должно быть четное количество параметров в формате 'insert|tableName|column1|value1|column2|value2|...|columnN|valueN', а ты прислал: 'insert|user|error'\n" +
                "Введи команду (или help для помощи):\n" +
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                // exit
                "До скорой встречи!\n", getData());
    }

    @Test
    public void testCreateTableSimple() {
        // given
        in.add("connect|Academy|postgres|1401198n");
        in.add("create|students|id|1|surname|Pushkin");
        in.add("exit");
        // when
        Main.main(new String[0]);
        // then
        assertEquals(
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                "Введите имя для создаваемой таблицы(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
                "Имя новой базы: users5\n" +
                "Введите имя для колонки PRIMARY KEY(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
                "Имя колонки PRIMARY KEY: id\n" +
                "Введите имя для еще одной колонки(имя должно начинаться с буквы) или '5' для создания базы с введенными колонками или '0' для выхода в основное меню\n" +
                "Имя еще одной колонки: name\n" +
                "Введите имя для еще одной колонки(имя должно начинаться с буквы) или '5' для создания базы с введенными колонками или '0' для выхода в основное меню\n" +
                "Имя еще одной колонки: password\n" +
                "Введите имя для еще одной колонки(имя должно начинаться с буквы) или '5' для создания базы с введенными колонками или '0' для выхода в основное меню\n" +
                "Таблица users5 была успешно создана.\n" +
                "+--+----+--------+\n" +
                "|id|name|password|\n" +
                "+--+----+--------+\n" +
                "Введи команду (или help для помощи):\n" +
                "Вы уверены, что хотите удалить users5? Y/N\n" +
                "Таблица users5 была успешно удалена.\n" +
                "Введи команду (или help для помощи):\n" +
                "Успех!\n" +
                "Введи команду (или help для помощи):\n" +
                "До скорой встречи!\n", getData());
    }

//    @Test
//    public void testTableSimpleExit() {
//        // given
//        in.add(commandConnect);
//        in.add("createTable");
//        in.add("");
//        in.add("1");
//        in.add("0");
//        in.add("createTable");
//        in.add("user6");
//        in.add("");
//        in.add("0");
//        in.add("createTable");
//        in.add("user6");
//        in.add("id");
//        in.add("");
//        in.add("0");
//        in.add("createTable");
//        in.add("user6");
//        in.add("id");
//        in.add("name");
//        in.add("0");
//        in.add(commandDisconnect);
//        in.add("exit");
//        // when
//        Main.main(new String[0]);
//        // then
//        assertEquals(pleaseConnect +
//                // connect
//                "Успех!\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Введите имя для создаваемой таблицы(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
//                "Нужно ввести имя для создаваемой таблицы, а вы вели пустую строку\n" +
//                "Введите имя для создаваемой таблицы(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
//                "Имя должно начинаться с буквы, а у тебя начинается с '1'\n" +
//                "Введите имя для создаваемой таблицы(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
//                "Выход в основное меню\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Введите имя для создаваемой таблицы(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
//                "Имя новой базы: user6\n" +
//                "Введите имя для колонки PRIMARY KEY(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
//                "Нужно ввести имя для колонки PRIMARY KEY, а вы вели пустую строку\n" +
//                "Введите имя для колонки PRIMARY KEY(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
//                "Выход в основное меню\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Введите имя для создаваемой таблицы(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
//                "Имя новой базы: user6\n" +
//                "Введите имя для колонки PRIMARY KEY(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
//                "Имя колонки PRIMARY KEY: id\n" +
//                "Введите имя для еще одной колонки(имя должно начинаться с буквы) или '5' для создания базы с введенными колонками или '0' для выхода в основное меню\n" +
//                "Нужно ввести имя для колонки, а вы вели пустую строку\n" +
//                "Введите имя для еще одной колонки(имя должно начинаться с буквы) или '5' для создания базы с введенными колонками или '0' для выхода в основное меню\n" +
//                "Выход в основное меню\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Введите имя для создаваемой таблицы(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
//                "Имя новой базы: user6\n" +
//                "Введите имя для колонки PRIMARY KEY(имя должно начинаться с буквы) или '0' для выхода в основное меню\n" +
//                "Имя колонки PRIMARY KEY: id\n" +
//                "Введите имя для еще одной колонки(имя должно начинаться с буквы) или '5' для создания базы с введенными колонками или '0' для выхода в основное меню\n" +
//                "Имя еще одной колонки: name\n" +
//                "Введите имя для еще одной колонки(имя должно начинаться с буквы) или '5' для создания базы с введенными колонками или '0' для выхода в основное меню\n" +
//                "Выход в основное меню\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Успех!\n" +
//                "Введи команду (или help для помощи):\n" +
//                "До скорой встречи!\n", getData());
//    }
//
//    @Test
//    public void testInsertSimple() {
//        // given
//        in.add(commandConnect);
//        in.add("insertTable|users2");
//        in.add("1");
//        in.add("Frank");
//        in.add("****");
//        in.add("clear|users2");
//        in.add(commandDisconnect);
//        in.add("exit");
//        // when
//        Main.main(new String[0]);
//        // then
//        assertEquals(pleaseConnect +
//                // connect
//                "Успех!\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Введите значение в поле 'id' или введите '0' для выхода в основное меню.\n" +
//                "Введите значение в поле 'username' или введите '0' для выхода в основное меню.\n" +
//                "Введите значение в поле 'password' или введите '0' для выхода в основное меню.\n" +
//                "В таблице 'users2' была успешно добавлена запись:\n" +
//                "+--+--------+--------+\n" +
//                "|id|username|password|\n" +
//                "+--+--------+--------+\n" +
//                "|1 |Frank   |****    |\n" +
//                "+--+--------+--------+\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Таблица users2 была успешно очищена.\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Успех!\n" +
//                "Введи команду (или help для помощи):\n" +
//                "До скорой встречи!\n", getData());
//    }
//
//    @Test
//    public void testInsertSimpleExit() {
//        // given
//        in.add(commandConnect);
//        in.add("insertTable|users2");
//        in.add("");
//        in.add("1");
//        in.add("0");
//        in.add("insertTable|users2");
//        in.add("1");
//        in.add("");
//        in.add("Frank");
//        in.add("0");
//        in.add("insertTable|users2");
//        in.add("1");
//        in.add("Frank");
//        in.add("");
//        in.add("0");
//        in.add(commandDisconnect);
//        in.add("exit");
//        // when
//        Main.main(new String[0]);
//        // then
//        assertEquals(pleaseConnect +
//                // connect
//                "Успех!\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Введите значение в поле 'id' или введите '0' для выхода в основное меню.\n" +
//                "Нужно ввести имя для колонки PRIMARY KEY, а вы вели пустую строку\n" +
//                "Введите значение в поле 'id' или введите '0' для выхода в основное меню.\n" +
//                "Введите значение в поле 'username' или введите '0' для выхода в основное меню.\n" +
//                "Выход в основное меню\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Введите значение в поле 'id' или введите '0' для выхода в основное меню.\n" +
//                "Введите значение в поле 'username' или введите '0' для выхода в основное меню.\n" +
//                "Нужно ввести имя для колонки PRIMARY KEY, а вы вели пустую строку\n" +
//                "Введите значение в поле 'username' или введите '0' для выхода в основное меню.\n" +
//                "Введите значение в поле 'password' или введите '0' для выхода в основное меню.\n" +
//                "Выход в основное меню\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Введите значение в поле 'id' или введите '0' для выхода в основное меню.\n" +
//                "Введите значение в поле 'username' или введите '0' для выхода в основное меню.\n" +
//                "Введите значение в поле 'password' или введите '0' для выхода в основное меню.\n" +
//                "Нужно ввести имя для колонки PRIMARY KEY, а вы вели пустую строку\n" +
//                "Введите значение в поле 'password' или введите '0' для выхода в основное меню.\n" +
//                "Выход в основное меню\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Успех!\n" +
//                "Введи команду (или help для помощи):\n" +
//                "До скорой встречи!\n", getData());
//    }
//
//    @Test
//    public void testDropDatabaseException() {
//        // given
//        in.add(commandConnect);
//        in.add("dropDB|sqlcmd965823756925");
//        in.add("y");
//        in.add(commandDisconnect);
//        in.add("exit");
//        // when
//        Main.main(new String[0]);
//        // then
//
//        assertEquals(pleaseConnect +
//                // connect
//                "Успех!\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Вы уверены, что хотите удалить sqlcmd965823756925? Y/N\n" +
//                "Неудача! по причине: ERROR: database \"sqlcmd965823756925\" does not exist\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Успех!\n" +
//                "Введи команду (или help для помощи):\n" +
//                "До скорой встречи!\n", getData());
//    }
//
//    @Ignore // тест занимает много времени, половина от всех вместе взятых...
//    @Test
//    public void testCreateDropDatabase() {
//        // given
//        in.add(commandConnect);
//        in.add("createDB|sqlcmd9hope9never9exist");
//        in.add("dropDB|sqlcmd9hope9never9exist");
//        in.add("y");
//        in.add(commandDisconnect);
//        in.add("exit");
//        // when
//        Main.main(new String[0]);
//        // then
//
//        assertEquals(
//                // connect
//                "Успех!\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Database sqlcmd9hope9never9exist была успешно создана.\n" +
//                "Введи команду (или help для помощи):\n" +
//                "Вы уверены, что хотите удалить sqlcmd9hope9never9exist? Y/N\n" +
//                "Database 'sqlcmd9hope9never9exist' была успешно удалена.\n" +
//                "Введи команду (или help для помощи):\n" +
//                // exit
//                "До скорой встречи!\n", getData());
//    }

}
