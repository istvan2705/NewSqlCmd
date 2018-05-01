package ua.com.juja.sqlcmd.model;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.view.View;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {


       private DatabaseManager manager;
       private View view;

    public JDBCDatabaseManagerTest(View view) {
        this.view = view;
    }

    @Before
        public void setup() throws SQLException {

            manager = new JDBCDatabaseManager();
            manager.connect("Academy", "postgres", "1401198n");
        }

        @Test
        public void testGetAllTableNames() throws SQLException {
            String[] tableNames = manager.getTableNames();
            assertEquals("[teachers, students, workers]", Arrays.toString(tableNames));
        }



    }


