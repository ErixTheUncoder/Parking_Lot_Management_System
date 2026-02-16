package db;

import java.sql.*;

public class DatabaseConnection {

    private static Connection conn = null;

    // Database file will be created in project root
    private static final String DB_URL = "jdbc:sqlite:parking.db";

    /**
     * Get database connection (Singleton style)
     */
    public static Connection getConnection() {
        try {
            // 🔥 IMPORTANT: Load SQLite driver manually
            Class.forName("org.sqlite.JDBC");

            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(DB_URL);
                System.out.println("Database connection established.");
            }

        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return conn;
    }

    /**
     * Close database connection
     */
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    /**
     * Initialize database using schema.sql
     */
    public static void initializeDatabase(String schemaPath) {

        Connection connection = getConnection();

        if (connection == null) {
            System.err.println("Cannot initialize database — connection failed.");
            return;
        }

        try (Statement stmt = connection.createStatement()) {

            String schema;

            // ===== Try loading from resources first =====
            java.io.InputStream is = DatabaseConnection.class
                    .getClassLoader()
                    .getResourceAsStream("schema.sql");

            if (is != null) {
                schema = new String(is.readAllBytes());
            } else {
                // ===== Otherwise load from file system =====
                java.nio.file.Path path = java.nio.file.Paths.get(schemaPath);
                schema = new String(java.nio.file.Files.readAllBytes(path));
            }

            executeSQLScript(stmt, schema);

            System.out.println("Database initialized successfully.");

        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Execute multiple SQL statements separated by semicolon
     */
    private static void executeSQLScript(Statement stmt, String script) throws SQLException {

        // Remove Windows line endings
        script = script.replace("\r", "");

        StringBuilder command = new StringBuilder();

        for (String line : script.split("\n")) {

            line = line.trim();

            // Skip comments and empty lines
            if (line.isEmpty() || line.startsWith("--")) {
                continue;
            }

            command.append(line).append(" ");

            // Execute when semicolon reached
            if (line.endsWith(";")) {

                String sql = command.toString().trim();

                // Remove trailing semicolon
                sql = sql.substring(0, sql.length() - 1);

                stmt.execute(sql);

                command.setLength(0);
            }
        }
    }

}
