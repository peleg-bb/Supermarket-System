package HR.DataAccessLayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sqlitetutorial.net
 */
public class Connect {
    public Connection conn;
    public final String url = "jdbc:sqlite:HR_Deliveries_Database.db";
    private static Connect instance;

    public static Connect getInstance() {
        if (instance == null) {
            instance = new Connect();
        }
        return instance;
    }

    private Connect() {
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            createTables();

        } catch (SQLException ignored) {
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ignored) {
            }
        }
    }

    public void createTables() throws SQLException {
        try (Statement statement = createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS Employees (\n" +
                    "id INTEGER,\n" +
                    "name VARCHAR(200),\n" +
                    "password VARCHAR(200),\n" +
                    "salary REAL,\n" +
                    "termsOfEmployment VARCHAR(255),\n" +
                    "familyStatus VARCHAR(15),\n" +
                    "isStudent INTEGER,\n" +
                    "bankAccount INTEGER,\n" +
                    "employmentDate DATE,\n" +
                    "currentSalary REAL,\n" +
                    "PRIMARY KEY (id)\n" +
                    ");";
            statement.execute(query);
            query = "CREATE TABLE IF NOT EXISTS Roles (\n" +
                    "id INTEGER,\n" +
                    "jobType VARCHAR(30),\n" +
                    "FOREIGN KEY (id) REFERENCES Employees(id) ON DELETE CASCADE" +
                    ");";
            statement.execute(query);
            query = "CREATE TABLE IF NOT EXISTS Stores (\n" +
                    "id INTEGER,\n" +
                    "store VARCHAR(30),\n" +
                    "FOREIGN KEY (id) REFERENCES Employees(id) ON DELETE CASCADE" +
                    ");";
            statement.execute(query);
            query = "CREATE TABLE IF NOT EXISTS Shifts (" +
                    "store VARCHAR(20)," +
                    "shiftType VARCHAR(8)," + //MORNING/EVENING
                    "day VARCHAR(2)," +
                    "month VARCHAR(2)," +
                    "year VARCHAR(4)," +
                    "start VARCHAR(6)," +
                    "end VARCHAR(6)," +
                    "confirmed INTEGER," +
                    "PRIMARY KEY (store, shiftType, day, month, year)" +
                    ");";
            statement.execute(query);
            query = "CREATE TABLE IF NOT EXISTS EmployeesInShift(" +
                    "store VARCHAR(20)," +
                    "shiftType VARCHAR(8)," + // MORNING/EVENING
                    "day VARCHAR(2)," +
                    "month VARCHAR(2)," +
                    "year VARCHAR(4)," +
                    "employeeID INTEGER," +
                    "job VARCHAR(30)," +
                    "PRIMARY KEY (employeeID, store, shiftType, day, month, year)," +
                    "FOREIGN KEY (store, shiftType, day, month, year) REFERENCES Shifts(store, shiftType, day, month, year)" +
                    " ON DELETE CASCADE," +
                    "FOREIGN KEY (employeeID) REFERENCES Employees(id) ON DELETE CASCADE" +
                    ");";
            statement.execute(query);
            query = "CREATE TABLE IF NOT EXISTS Availability(" +
                    "store VARCHAR(20)," +
                    "shiftType VARCHAR(8)," + // MORNING/EVENING
                    "day VARCHAR(2)," +
                    "month VARCHAR(2)," +
                    "year VARCHAR(4)," +
                    "employeeID INTEGER," +
                    "availabilityOrConstraint INTEGER," + //Indicator of 1 - availability, 0 - manager constraint
                    "PRIMARY KEY (store, shiftType, day, month, year, employeeID)," +
                    "FOREIGN KEY (store, shiftType, day, month, year) REFERENCES Shifts(store, shiftType, day, month, year)" +
                    " ON DELETE CASCADE," +
                    "FOREIGN KEY (employeeID) REFERENCES Employees(id) ON DELETE CASCADE" +
                    ")";
            statement.execute(query);
            query = "CREATE TABLE IF NOT EXISTS ShiftEvents(" +
                    "store VARCHAR(20)," +
                    "shiftType VARCHAR(8)," + // MORNING/EVENING
                    "day VARCHAR(2)," +
                    "month VARCHAR(2)," +
                    "year VARCHAR(4)," +
                    "employeeId INTEGER," +
                    "productId INTEGER," +
                    "FOREIGN KEY (store, shiftType, day, month, year) REFERENCES Shifts(store, shiftType, day, month, year)" +
                    " ON DELETE CASCADE" +
                    ")";
            statement.execute(query);

        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnect();
        }
    }

    public Statement createStatement() throws SQLException {
        conn = DriverManager.getConnection(url);
        return conn.createStatement();
    }

    public void closeConnect() throws SQLException {
        conn.close();
    }


    public List<HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<>();

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }
        return list;
    }

    public List<HashMap<String, Object>> executeQuery(String query, Object... params) throws SQLException {
        try {
            createStatement();
            PreparedStatement statement = conn.prepareStatement(query);
            for (int i = 0; i < params.length; i++)
                statement.setObject(i + 1, params[i]);
            ResultSet rs = statement.executeQuery();
            return convertResultSetToList((rs));
        } catch (SQLException throwable) {
            throw throwable;
        } finally {
            closeConnect();
        }
    }


    public int executeUpdate(String query, Object... params) throws SQLException {
        try {
            createStatement();
            PreparedStatement statement = conn.prepareStatement("PRAGMA foreign_keys = ON");
            statement.executeUpdate();
            statement = conn.prepareStatement(query);
            for (int i = 0; i < params.length; i++)
                statement.setObject(i + 1, params[i]);

            int res = statement.executeUpdate();
            return res;
        } catch (SQLException throwable) {
            throw throwable;
        } finally {
            closeConnect();
        }
    }

    public void deleteRecordsOfTables() throws SQLException {
        try (Statement stmt = createStatement()) {

            String query = "DROP TABLE IF EXISTS Employees";
            stmt.execute(query);
            query = "DROP TABLE IF EXISTS EmployeesInShift";
            stmt.execute(query);
            query = "DROP TABLE IF EXISTS Roles";
            stmt.execute(query);
            query = "DROP TABLE IF EXISTS Availability";
            stmt.execute(query);
            query = "DROP TABLE IF EXISTS Shifts";
            stmt.execute(query);
            query = "DROP TABLE IF EXISTS Stores";
            stmt.execute(query);
            createTables();


        } catch (SQLException e) {
            throw e;
        } finally {
            closeConnect();
        }

    }
}
