import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * The LoginApplication class represents a simple login system for a parking application.
 * It allows users to log in, create new accounts, and performs different actions based on user roles.
 */
public class LoginApplication {

    /**
     * The main method of the LoginApplication class.
     * It establishes a database connection, provides a menu for user interaction, and handles user choices.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Properties prop = new Properties();
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);

        try {
            // Load database configuration from properties file
            InputStream input = new FileInputStream("config.properties");
            prop.load(input);
            input.close(); // Close the input stream after use

            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String dbPassword = prop.getProperty("db.password");

            // Establish a database connection
            conn = DriverManager.getConnection(url, user, dbPassword);
            conn.setAutoCommit(false);

            int choice = 0;
            do {
                // Display menu options
                System.out.println("\nWelcome to Wolfpack Parking System!");
                System.out.println("-----------------------------------------------");
                System.out.println("1. Login");
                System.out.println("2. New Driver Sign-up");
                System.out.println("3. Exit");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        login(scanner, conn);
                        break;
                    case 2:
                        createUserAccount(scanner, conn);
                        break;
                    case 3:
                        System.out.println("Exiting. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 3);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close database connection and scanner
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                    if (conn.isClosed()) {
                        System.out.println("Database connection successfully closed.");
                    } else {
                        System.out.println("Database connection may still be open.");
                    }
                } else {
                    System.out.println("Database connection was already closed or not established.");
                }
            } catch (SQLException e) {
                System.out.println("Error occurred while closing the database connection.");
                e.printStackTrace();
            }
            scanner.close(); // Close the scanner here
        }
    }

    /**
     * Handles the login process by taking user credentials, querying the database, and redirecting to appropriate user interfaces.
     *
     * @param scanner The Scanner object used for user input.
     * @param conn    The database Connection object.
     */
    private static void login(Scanner scanner, Connection conn) {

        scanner.nextLine();
        System.out.println("Enter user ID:");
        String userID = scanner.nextLine();
        System.out.println("Enter password:");
        String userPassword = scanner.nextLine();

        String sql = "SELECT status, password FROM User WHERE userID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String actualPassword = rs.getString("password");
                if (actualPassword.equals(userPassword)) {
                    String status = rs.getString("status");
                    switch (status) {
                        case "S":
                            System.out.println("Welcome NC State Student");
                            Driver st_driver = new Driver(userID);
                            st_driver.show_welcome_page();
                            break;
                        case "E":
                            System.out.println("Welcome NC State Staff");
                            Driver e_driver = new Driver(userID);
                            e_driver.show_welcome_page();
                            break;
                        case "V":
                            System.out.println("Welcome NC State Visitor");
                            Driver v_driver = new Driver(userID);
                            v_driver.show_welcome_page();
                            break;
                        case "A":
                            System.out.println("Welcome Admin");
                            Admin admin = new Admin(userID);
                            admin.show_welcome_page();
                            break;
                        case "SP":
                            System.out.println("Welcome Security Personnel");
                            SecurityPersonnel securityPersonnel = new SecurityPersonnel(userID);
                            securityPersonnel.showMenu();
                            break;
                        default:
                            System.out.println("Unknown status");
                            break;
                    }
                } else {
                    System.out.println("Wrong password!");
                    conn.commit();
                }
            } else {
                System.out.println("UserID or password not found! Please try again");
                conn.rollback();

            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    /**
     * Handles the process of creating a new user account by taking user details and inserting them into the database.
     *
     * @param scanner The Scanner object used for user input.
     * @param conn    The database Connection object.
     */
    private static void createUserAccount(Scanner scanner, Connection conn) {

        scanner.nextLine();
        System.out.println("Enter new user ID:");
        String newUserID = scanner.nextLine();
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter password:");
        String newPassword = scanner.nextLine();

        System.out.println("Choose the type of user you want to create:");
        System.out.println("S : Student");
        System.out.println("E : Employee");
        System.out.println("V : Visitor");
        System.out.println("Enter your choice: ");
        String userType = scanner.nextLine().toUpperCase();

        String insertUserSql = "INSERT INTO User (userID, name, password, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement insertUserStmt = conn.prepareStatement(insertUserSql)) {
            insertUserStmt.setString(1, newUserID);
            insertUserStmt.setString(2, name);
            insertUserStmt.setString(3, newPassword);
            insertUserStmt.setString(4, userType);

            int userResult = insertUserStmt.executeUpdate();

            if (userResult > 0) {
                boolean additionalInfoAdded = addAdditionalUserInfo(scanner, conn, newUserID);
                if (additionalInfoAdded) {
                    conn.commit(); // Commit transaction only if all data is inserted properly
                    System.out.println("Account created successfully.");
                } else {
                    conn.rollback(); // Rollback if unable to insert additional info
                    System.out.println("Failed to create account.");
                }
            } else {
                conn.rollback(); // Rollback if user was not inserted
                System.out.println("Failed to create account.");
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback on exception
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Adds additional user information in the Driver table
     * @param scanner The Scanner object used for user input.
     * @param conn    The database Connection object.
     * @param userID  The user ID for whom additional information is being added.
     * @return True if additional information is added successfully, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    private static boolean addAdditionalUserInfo(Scanner scanner, Connection conn, String userID) throws SQLException {
                System.out.println("Enter disability status (D/N), D: Disabled, N: No:");
                String disabilityStatus = scanner.nextLine().toUpperCase();
                return insertIntoDriver(conn, userID, disabilityStatus);

    }

    /**
     * Inserts additional information into the Driver table based on user type.
     *
     * @param conn            The database Connection object.
     * @param userID          The user ID for whom additional information is being added.
     * @param disabilityStatus The disability status of the user.
     * @return True if the information is added successfully, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    private static boolean insertIntoDriver(Connection conn, String userID, String disabilityStatus) throws SQLException {
        String sql = "INSERT INTO Driver (userID, disabilityStatus) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setString(2, disabilityStatus);
            return stmt.executeUpdate() > 0;
        }
    }
}
