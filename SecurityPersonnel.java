import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SecurityPersonnel {
	private Connection conn;
    private Properties prop;
    private String userID;
    private Scanner scanner;

    public SecurityPersonnel(String userID) {
        this.userID = userID;
        this.scanner = new Scanner(System.in);

        try {
            FileInputStream input = new FileInputStream("config.properties");
            prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            System.out.println("Connected to the database successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void showMenu() {
    	
    	int choice = 0;
        do {
            System.out.println("\nWelcome, Security Personnel!");
            System.out.println("-----------------------------------------------");
            System.out.println("1. Create Citation");
            System.out.println("2. Update Citation");
            System.out.println("3. View Citation");
            System.out.println("4. View all Citations");
            System.out.println("5. Delete Citation");
            System.out.println("6. Update Payment Status");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            
           choice = scanner.nextInt();

            switch (choice) {
                case 1:
                	createCitation();
                    break;
                case 2:
                    updateCitation();
                    break;
                case 3:
                	viewCitation(); 
                    break;
                case 4:
                	viewAllCitations();
                    break;
                case 5:
                	deleteCitation();
                    break;
                case 6:
                	updatePaymentStatus();
                	break;
                case 7:
                    System.out.println("Exiting. Goodbye!");       
					LoginApplication.main(null);
                default:
                    System.out.println("Invalid choice. Please try selecting from the Menu again");
            }
        } while (choice != 6);
    }

    
    private void createCitation() {
        scanner.nextLine();
        System.out.print("Enter Citation ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter carLicense Number: ");
        String carLicenseNo = scanner.nextLine();
        System.out.print("Enter Parking Lot ID: ");
        String parkingLotId = scanner.nextLine();        
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String citationDate = currentDate.format(dateFormatter);
        String citationTime = currentTime.format(timeFormatter);
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter Fee Amount: ");
        String fee = scanner.nextLine();
        System.out.print("Enter the Payment Status: ");
        String paymentStatus = scanner.next();
        String sql = "INSERT INTO Citations (citationNumber, carLicenseNo, parkingLotID, citationDate, citationTime, category, fee, paymentStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, carLicenseNo);
            pstmt.setString(3, parkingLotId);
            pstmt.setString(4, citationDate);
            pstmt.setString(5, citationTime);
            pstmt.setString(6, category);
            pstmt.setString(7, fee);
            pstmt.setString(8, paymentStatus);
            
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("New Citation Has Been Created Successfully.");
                conn.commit();
            } else {
                System.out.println("Citation Creation Unsuccessful.");
                conn.rollback();
            }
        } catch (SQLException e) {  	
            System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }
    
    private void updateCitation() {

    	scanner.nextLine();
        System.out.print("Enter Citation ID to update: ");
        String citationId = scanner.nextLine();
        System.out.println("Select the attribute to update:");
        System.out.println("-----------------------------------------------");
        System.out.println("1. Car License Number");
        System.out.println("2. Parking Lot ID");
        System.out.println("3. Category");
        System.out.println("4. Fee Amount");
        System.out.println("5. Appeal");
        System.out.print("Enter your choice (1-5): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 
        String newValue;
        switch (choice) {
            case 1:
                System.out.print("Enter new Car License Number: ");
                newValue = scanner.nextLine();
                updateAttribute(citationId, "carLicenseNo", newValue);
                break;
            case 2:
                System.out.print("Enter new Parking Lot ID: ");
                newValue = scanner.nextLine();
                updateAttribute(citationId, "parkingLotID", newValue);
                break;
            case 3:
                System.out.print("Enter new Category: ");
                newValue = scanner.nextLine();
                updateAttribute(citationId, "category", newValue);
                break;
            case 4:
                System.out.print("Enter new Fee Amount: ");
                newValue = scanner.nextLine();
                updateAttribute(citationId, "fee", newValue);
                break;
            case 5: 
            	System.out.print("Enter Appeal Yes/No: ");
                newValue = scanner.nextLine();
                updateAttribute(citationId, "appeal", newValue);
                break;
            default:
                System.out.println("Invalid choice. No updates performed.");
        }
    }


    private void updateAttribute(String citationId, String attributeName, String newValue) {
        String sql = "UPDATE Citations SET " + attributeName + " = ? WHERE citationNumber = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newValue);
            pstmt.setString(2, citationId);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Citation updated successfully.");
                conn.commit();
            } else {
     
                System.out.println("No citation found with the given ID. Update unsuccessful.");
                conn.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }
    
    private void viewAllCitations() {
        String sql = "SELECT * FROM Citations";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
            	System.out.println("Citation Number: " + rs.getString("citationNumber") +
                        ", Car License Number: " + rs.getString("carLicenseNo") +
                        ", Parking Lot ID: " + rs.getString("parkingLotID") +
                        ", Category: " + rs.getString("category") +
                        ", Fee: " + rs.getString("fee") +
                        ", Payment Status: " + rs.getString("paymentStatus") +
                        ", Appeal: " + rs.getString("appeal"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void viewCitation() {
    	scanner.nextLine();
        System.out.print("Enter Citation Number: ");
        String citationNumber = scanner.nextLine();

        String sql = "SELECT * FROM Citations WHERE citationNumber = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, citationNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Citation Number: " + rs.getString("citationNumber") +
                        ", Car License Number: " + rs.getString("carLicenseNo") +
                        ", Parking Lot ID: " + rs.getString("parkingLotID") +
                        ", Category: " + rs.getString("category") +
                        ", Fee: " + rs.getString("fee") +
                        ", Payment Status: " + rs.getString("paymentStatus") +
                        ", Appeal: " + rs.getString("appeal"));
            } else {
                System.out.println("Citation with number " + citationNumber + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void deleteCitation() {
        scanner.nextLine();
        System.out.print("Enter Citation Number you want to delete: ");
        String citationNumber = scanner.nextLine();

        String sql = "DELETE FROM Citations WHERE citationNumber = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, citationNumber);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Citation has been deleted.");
                conn.commit();
            } else {
                System.out.println("Could not delete citation. Please check if the number is correct.");
                conn.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }
    
    private void updatePaymentStatus() {
        scanner.nextLine();
        System.out.print("Enter Citation Number to update payment status: ");
        String citationNumber = scanner.nextLine();

        System.out.print("Enter new Payment Status: ");
        String newPaymentStatus = scanner.nextLine();

        String sql = "UPDATE Citations SET paymentStatus = ? WHERE citationNumber = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPaymentStatus);
            pstmt.setString(2, citationNumber);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Payment status updated successfully.");
                conn.commit();
            } else {
                System.out.println("No citation found with the given number. Update unsuccessful.");
                conn.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }

    public void getLotCitationCount(String timeRange) {
        String sql;

        if (timeRange == null || timeRange.isEmpty()) {
            // Default to monthly if time range is not selected
            sql = "SELECT parkingLotID, zone, COUNT(*) as citationCount FROM Citations WHERE MONTH(citationDate) = MONTH(CURRENT_DATE()) GROUP BY parkingLotID, zone";
        } else {
            // Customize the SQL query based on the selected time range (you may need to adjust this based on your database schema)
            // Example: Monthly, Weekly, Daily
            sql = "SELECT parkingLotID, zone, COUNT(*) as citationCount FROM Citations WHERE DATE_SUB(CURDATE(), INTERVAL 1 " + timeRange.toUpperCase() + ") <= citationDate GROUP BY parkingLotID, zone";
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            List<String> citationCounts = new ArrayList<>();

            while (rs.next()) {
                String parkingLotID = rs.getString("parkingLotID");
                String zone = rs.getString("zone");
                int count = rs.getInt("citationCount");
                
                System.out.println("(" + parkingLotID + ", " + zone + "): " + count + " citations");
            }

            if (citationCounts.isEmpty()) {
                System.out.println("No citation counts found for the specified time range.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getZones() {
        String sql = "SELECT parkingLotID, zone FROM ParkingLots";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String parkingLotID = rs.getString("parkingLotID");
                String zone = rs.getString("zone");

                System.out.println("(" + parkingLotID + ", " + zone + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getViolationCarCount() {
        String sql = "SELECT DISTINCT carLicenseNo FROM Citations WHERE paymentStatus = 'Unpaid'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String carLicenseNo = rs.getString("carLicenseNo");
                System.out.println(carLicenseNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPermittedEmployees(String parkingLotID) {
        if (parkingLotID == null || parkingLotID.isEmpty()) {
            throw new IllegalArgumentException("Parking Lot ID cannot be null or empty.");
        }

        String sql = "SELECT DISTINCT driverID FROM Permits WHERE parkingLotID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, parkingLotID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String driverID = rs.getString("driverID");
                System.out.println(driverID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getPermitInformation(String userID) {
        if (userID == null || userID.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }

        String sql = "SELECT * FROM Permits WHERE driverID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String permitID = rs.getString("permitID");
                String parkingLotID = rs.getString("parkingLotID");
                // Add more fields as needed

                System.out.println("Permit ID: " + permitID);
                System.out.println("Parking Lot ID: " + parkingLotID);
                // Print additional permit information
            } else {
                System.out.println("No permit information found for user ID: " + userID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAvailableSpaces(String parkingLotID, String spaceType) {
        if (parkingLotID == null || parkingLotID.isEmpty() || spaceType == null || spaceType.isEmpty()) {
            throw new IllegalArgumentException("Parking Lot ID and Space Type cannot be null or empty.");
        }

        String sql = "SELECT spaceNumber FROM ParkingSpaces WHERE parkingLotID = ? AND spaceType = ? AND occupied = 0";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, parkingLotID);
            pstmt.setString(2, spaceType);
            ResultSet rs = pstmt.executeQuery();

            List<String> availableSpaces = new ArrayList<>();

            while (rs.next()) {
                String spaceNumber = rs.getString("spaceNumber");
                availableSpaces.add(spaceNumber);
            }

            if (!availableSpaces.isEmpty()) {
                System.out.println("Available Spaces for Parking:");
                for (String space : availableSpaces) {
                    System.out.println(space);
                }
            } else {
                System.out.println("No available spaces found for the specified criteria.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
