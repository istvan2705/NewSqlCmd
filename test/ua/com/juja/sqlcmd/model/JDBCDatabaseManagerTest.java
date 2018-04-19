package ua.com.juja.sqlcmd.model;


import org.junit.Before;
import org.junit.Test;
import ua.com.juja.sqlcmd.view.View;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {


       private DatabaseManager manager;
    View view;

    public JDBCDatabaseManagerTest(View view) {
        this.view = view;
    }

    @Before
        public void setup() {

            manager = new JDBCDatabaseManager(view);
            manager.connect("Academy", "postgres", "1401198n");
        }

        @Test
        public void testGetAllTableNames() {
            String[] tableNames = manager.getTableNames();
            assertEquals("[teachers, students, workers]", Arrays.toString(tableNames));
        }



    }


