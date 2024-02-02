package a2_2001040230;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection to DataBase
 */
public class DbConnections {

    private static String databasePath = "jdbc:sqlite:library.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(databasePath);
    }
}
