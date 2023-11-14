import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.InputStream;

public class LoginApplication {

    public static void main(String[] args) {
        Properties prop = new Properties();

        try (Scanner scanner = new Scanner(System.in);
             InputStream input = new FileInputStream("config.properties")) {
            
            prop.load(input);
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String dbPassword = prop.getProperty("db.password");

            System.out.println("Enter user ID:");
            String userID = scanner.nextLine();
            System.out.println("Enter password:");
            String userPassword = scanner.nextLine();

            try (Connection conn = DriverManager.getConnection(url, user, dbPassword)) {
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
                                case "E":
                                case "V":
                                    System.out.println("Welcome Driver");
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
                            System.out.println("Wrong password.");
                        }
                    } else {
                        System.out.println("UserID not found. Would you like to create a new account? (yes/no)");
                        String response = scanner.nextLine();
                        if ("yes".equalsIgnoreCase(response)) {
                            createUserAccount(scanner, conn);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createUserAccount(Scanner scanner, Connection conn) throws SQLException {
        System.out.println("Enter new user ID:");
        String newUserID = scanner.nextLine();
        System.out.println("Enter name:");
        String name = scanner.nextLine();
        System.out.println("Enter password:");
        String newPassword = scanner.nextLine();

        System.out.println("Choose the type of user you want to create:");
        System.out.println("A : Admin");
        System.out.println("S : Student");
        System.out.println("E : Employee");
        System.out.println("V : Visitor");
        System.out.println("SP : Security Personnel");
        System.out.println("Enter your choice: ");
        String userType = scanner.nextLine().toUpperCase();

        conn.setAutoCommit(false); // Start transaction
        String insertUserSql = "INSERT INTO User (userID, name, password, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement insertUserStmt = conn.prepareStatement(insertUserSql)) {
            insertUserStmt.setString(1, newUserID);
            insertUserStmt.setString(2, name);
            insertUserStmt.setString(3, newPassword);
            insertUserStmt.setString(4, userType);

            int userResult = insertUserStmt.executeUpdate();

            if (userResult > 0) {
                boolean additionalInfoAdded = addAdditionalUserInfo(scanner, conn, newUserID, userType);
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
            conn.rollback(); // Rollback on exception
            throw e;
        } finally {
            conn.setAutoCommit(true); // Reset auto commit behavior
        }
    }

    private static boolean addAdditionalUserInfo(Scanner scanner, Connection conn, String userID, String userType) throws SQLException {
        switch (userType) {
            case "A":
                return insertIntoAdmin(conn, userID);
            case "SP":
                return insertIntoSecurityPersonnel(conn, userID);
            case "S":
            case "E":
            case "V":
                System.out.println("Enter disability status (D/N), D: Diabled, N: No:");
                String disabilityStatus = scanner.nextLine().toUpperCase();
                return insertIntoDriver(conn, userID, disabilityStatus);
            default:
                return true;
        }
    }

    private static boolean insertIntoAdmin(Connection conn, String userID) throws SQLException {
        String sql = "INSERT INTO Admin (userID) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            return stmt.executeUpdate() > 0;
        }
    }

    private static boolean insertIntoSecurityPersonnel(Connection conn, String userID) throws SQLException {
        String sql = "INSERT INTO SecurityPersonnel (userID) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            return stmt.executeUpdate() > 0;
        }
    }

    private static boolean insertIntoDriver(Connection conn, String userID, String disabilityStatus) throws SQLException {
        String sql = "INSERT INTO Driver (userID, disabilityStatus) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            stmt.setString(2, disabilityStatus);
            return stmt.executeUpdate() > 0;
        }
    }
}
