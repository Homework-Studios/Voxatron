package testing;

import java.sql.*;

public class Database {
    public static String NOTING_FOUND = "N/A";

    Connection connection;
//TODO: Get database to store data
    String host = "localhost";
    String port = "3306";
    String database = "database";
    String username = "root";
    String password = "password";

    public static void databaseTest() {
        Database database = new Database();
        database.connect();
        String test = database.getStringByString("dev", "test1", "connection_test", "value");
        if(test.equals(NOTING_FOUND)) {
            database.addLine("dev", "connection_test", "value");
            database.setStringByString("dev", "test1", "connection_test", "value", "test");
        }
        else System.out.println("test completed successfully!");
        System.out.println(test);
        database.disconnect();
    }

    public Database(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public Database() {
    }

    public void connect() {
        System.out.println("Connecting to MySQL Database..." + host + ":" + port + "/" + database);
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("MySQL disconnected!");
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean hasConnection() {
        return connection != null;
    }

    public Connection getConnection() {
        if (!hasConnection()) {
            connect();
        }
        return connection;
    }

    public String getStringByString(String table, String key, String keyColumn, String valueColumn) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "SELECT " + valueColumn + " FROM " + table + " WHERE " + keyColumn + " = ?;"
        )) {
            stmt.setString(1, key);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                String result = resultSet.getString(valueColumn);
                return result;
            }
            stmt.close();
            return "N/A";
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public boolean setStringByString(String table, String key, String keyColumn, String value, String valueColumn) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "UPDATE " + table + " SET " + valueColumn + " = ? WHERE " + keyColumn + " = ?"
        )) {
            stmt.setString(1, value);
            stmt.setString(2, key);
            System.out.println(key + " was set to " + value + " in " + table);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addLine(String table, String columnName, String columnValue) {
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO " + table + "(" + columnName + ") select '" + columnValue + "'"
        )) {
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
