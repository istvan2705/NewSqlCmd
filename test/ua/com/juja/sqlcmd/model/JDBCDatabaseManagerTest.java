package ua.com.juja.sqlcmd.model;


import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JDBCDatabaseManagerTest {


       private DatabaseManager manager;

        @Before
        public void setup() {
            manager = new JDBCDatabaseManager();
            manager.connect("Academy", "postgres", "1401198n");
        }

        @Test
        public void testGetAllTableNames() {
            String[] tableNames = manager.getTableNames();
            assertEquals("[teachers, students, workers]", Arrays.toString(tableNames));
        }



    }


