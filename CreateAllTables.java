import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class CreateAllTables {

    public static void main(String[] args) {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            // Load the properties file
            input = new FileInputStream("config.properties");
            prop.load(input);

            // Get the property values
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");
            String filePath = prop.getProperty("queries.create_tables.path");

            // Create a connection to the database
            Connection conn = DriverManager.getConnection(url, user, password);

            // Read file line by line
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // Remove single line comments
                line = line.split("--")[0].split("#")[0];
                sb.append(line);
            }
            reader.close();

            // Split file content into separate queries
            String[] queries = sb.toString().split(";");

            // Execute each query
            Statement stmt = conn.createStatement();
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query);
                    String tableName = extractTableName(query);
                    System.out.println("CREATED THE TABLE " + tableName + ".");
                }
            }

            // Close the statement and the connection
            stmt.close();
            conn.close();
            System.out.println("All queries executed successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String extractTableName(String query) {
        // Splits the query by whitespace characters into parts
        String[] parts = query.split("\\s+");
        for (int i = 0; i < parts.length; i++) {
            // Checks if the current part is "TABLE", considering "IF EXISTS" clause
            if (parts[i].equalsIgnoreCase("TABLE")) {
                // Checks for "IF EXISTS" clause which would add two more words before the actual table name
                if (i + 1 < parts.length && parts[i + 1].equalsIgnoreCase("IF")) {
                    // The table name should follow the words "IF EXISTS"
                    return parts[i + 3];
                } else {
                    // The table name should follow the word "TABLE"
                    return parts[i + 1];
                }
            }
        }
        return "UNKNOWN"; // Fallback if table name could not be extracted
    }
    
}
