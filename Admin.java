import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.util.Scanner;


public class Admin {
    private Connection conn;
    private Properties prop;
    private String userID;
    private Scanner scanner;

    public Admin(String userID) {
        this.userID = userID;
        this.scanner = new Scanner(System.in);

        try {
            // Load properties file
            FileInputStream input = new FileInputStream("config.properties");
            prop = new Properties();
            prop.load(input);

            // Get the property values
            String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");

            // Create a connection to the database
            conn = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to the database successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void show_welcome_page() {
        int choice = 0;
        do {
            System.out.println("\nWelcome, Admin!");
            System.out.println("-----------------------------------------------");
            System.out.println("1. Add/Update/Delete/View Parking Lot");
            System.out.println("2. Add/Update/Delete/View Zone");
            System.out.println("3. Add/Update/Delete/View Space");
            System.out.println("4. Add/Update/Delete/View Permit");
            System.out.println("5. Add/Update/Delete/View Vehicle");
            System.out.println("6. Add New Admin");
            System.out.println("7. Add New Security Personnel");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    parkingLotMenu();
                    break;
                case 2:
                    zoneMenu();
                    break;
                case 3:
                    spaceMenu();
                    break;
                case 4:
                    permitMenu();
                    break;
                case 5:
                    vehicleMenu();
                    break;
                case 6:
                    insertIntoAdmin(conn);
                    break;
                case 7:
                    insertIntoSecurityPersonnel(conn);
                    break;
                case 8:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    private void parkingLotMenu() {
        int choice = 0;
        do {
        
        System.out.println("1. Add Parking Lot");
        System.out.println("2. Update Parking Lot");
        System.out.println("3. Delete Parking Lot");
        System.out.println("4. View all parking lots");
        
        System.out.println("5. Exit");
        
        choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addParkingLot();
                    break;
                case 2:
                    updateParkingLot();
                    break;
                case 3:
                    deleteParkingLot();
                    break;
                case 4:
                    viewParkingLots();
                    break;
                case 5:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void zoneMenu() {
        int choice = 0;
        do {
        
        System.out.println("1. Add Zone");
        System.out.println("2. Update Zone info");
        System.out.println("3. Delete Zone infot");
        System.out.println("4. View all zones");
        System.out.println("5. Exit");
        
        choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addZone();
                    break;
                case 2:
                    updateZone();
                    break;
                case 3:
                    deleteZone();
                    break;
                case 4:
                    viewZones();
                    break;
                case 5:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void spaceMenu() {

        int choice = 0;
        do {
        
        System.out.println("1. Add Space");
        System.out.println("2. Update Space");
        System.out.println("3. Delete Space");
        System.out.println("4. View all spaces");
        System.out.println("5. Exit");
        
        choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addSpace();
                    break;
                case 2:
                    updateSpace();
                    break;
                case 3:
                    deleteSpace();
                    break;
                case 4:
                    viewSpaces();
                    break;
                case 5:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void permitMenu() {
        int choice = 0;
        do {
        
        System.out.println("1. Add Permit");
        System.out.println("2. Update Permit");
        System.out.println("3. Delete Permitt");
        System.out.println("4. View all Permit");
        System.out.println("5. Exit");
        
        choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addPermit();
                    break;
                case 2:
                    updatePermit();
                    break;
                case 3:
                    deletePermit();
                    break;
                case 4:
                    viewPermit();
                    break;
                case 5:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void vehicleMenu() {
        int choice = 0;
        do {
        
        System.out.println("1. Add Vehicle");
        System.out.println("2. Update Vehicle");
        System.out.println("3. Delete Vehicle");
        System.out.println("4. View all Vehicle");
        System.out.println("5. Exit");
        
        choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    updateVehicle();
                    break;
                case 3:
                    deleteVehicle();
                    break;
                case 4:
                    viewVehicle();
                    break;
                case 5:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        
    }


    // Method to add a new parking lot
    public void addParkingLot() {
        scanner.nextLine();
        System.out.print("Enter Parking Lot ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Parking Lot Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Parking Lot Address: ");
        String address = scanner.nextLine();

        String sql = "INSERT INTO ParkingLot (parkingLotID, name, address) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, address);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("A new parking lot has been added.");
            } else {
                System.out.println("A parking lot could not be added.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to update an existing parking lot
    public void updateParkingLot() {
        scanner.nextLine();
        System.out.print("Enter Parking Lot ID to update: ");
        String id = scanner.nextLine();
        System.out.print("Enter new Parking Lot Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new Parking Lot Address: ");
        String address = scanner.nextLine();

        String sql = "UPDATE ParkingLot SET name = ?, address = ? WHERE parkingLotID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Parking lot has been updated.");
            } else {
                System.out.println("Could not update parking lot. Please check if the ID is correct.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to delete an existing parking lot
    public void deleteParkingLot() {
        scanner.nextLine();
        System.out.print("Enter Parking Lot ID to delete: ");
        String id = scanner.nextLine();

        String sql = "DELETE FROM ParkingLot WHERE parkingLotID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Parking lot has been deleted.");
            } else {
                System.out.println("Could not delete parking lot. Please check if the ID is correct.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to view all parking lots
    public void viewParkingLots() {
        String sql = "SELECT parkingLotID, name, address FROM ParkingLot";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("parkingLotID") +
                        ", Name: " + rs.getString("name") +
                        ", Address: " + rs.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
   

    public void addZone() {
        scanner.nextLine();
        System.out.print("Enter Parking Lot ID for the new zone: ");
        String parkingLotID = scanner.nextLine();
        System.out.print("Enter Zone ID: ");
        String zoneID = scanner.nextLine();

        String sql = "INSERT INTO Zone (parkingLotID, zoneID) VALUES (?, ?)";

        try {
            conn.setAutoCommit(false);  // Start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, parkingLotID);
                pstmt.setString(2, zoneID);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("A new zone has been added.");
                    conn.commit(); // Commit transaction
                } else {
                    System.out.println("A zone could not be added.");
                    conn.rollback(); // Rollback in case of error
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    // Method to update an existing zone
    public void updateZone() {
        scanner.nextLine();
        System.out.print("Enter Parking Lot ID for the zone to update: ");
        String parkingLotID = scanner.nextLine();
        System.out.print("Enter Zone ID to update: ");
        String zoneID = scanner.nextLine();

        

        System.out.println("The Zone table only contains ID fields which are not typically updated.");
    }

    // Method to delete an existing zone
    public void deleteZone() {
        scanner.nextLine();
        System.out.print("Enter Parking Lot ID for the zone to delete: ");
        String parkingLotID = scanner.nextLine();
        System.out.print("Enter Zone ID to delete: ");
        String zoneID = scanner.nextLine();

        String sql = "DELETE FROM Zone WHERE parkingLotID = ? AND zoneID = ?";

        try {
            conn.setAutoCommit(false);  // Start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, parkingLotID);
                pstmt.setString(2, zoneID);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Zone has been deleted.");
                    conn.commit(); // Commit transaction
                } else {
                    System.out.println("Could not delete zone. Please check the IDs are correct.");
                    conn.rollback(); // Rollback in case of error
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    // Method to view all zones
    public void viewZones() {
        scanner.nextLine();
        String sql = "SELECT parkingLotID, zoneID FROM Zone";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Parking Lot ID: " + rs.getString("parkingLotID") +
                        ", Zone ID: " + rs.getString("zoneID"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to add a new space
    public void addSpace() {
        scanner.nextLine();
        System.out.print("Enter Parking Lot ID for the new space: ");
        String parkingLotID = scanner.nextLine();
        System.out.print("Enter Zone ID for the new space: ");
        String zoneID = scanner.nextLine();
        System.out.print("Enter Space Number: ");
        int spaceNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        System.out.print("Enter Space Type (Regular/Handicap/Compact/Electric): ");
        String spaceType = scanner.nextLine();
        System.out.print("Enter Availability Status (Y/N): ");
        String availability = scanner.nextLine();

        String sql = "INSERT INTO Space (parkingLotID, zoneID, spaceNumber, spaceType, availability) VALUES (?, ?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, parkingLotID);
                pstmt.setString(2, zoneID);
                pstmt.setInt(3, spaceNumber);
                pstmt.setString(4, spaceType);
                pstmt.setString(5, availability);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("A new space has been added.");
                    conn.commit(); // Commit the transaction
                } else {
                    System.out.println("A space could not be added.");
                    conn.rollback(); // Rollback in case of error
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    // Method to update an existing space
    public void updateSpace() {
        scanner.nextLine();
        System.out.print("Enter Parking Lot ID for the space to update: ");
        String parkingLotID = scanner.nextLine();
        System.out.print("Enter Zone ID for the space to update: ");
        String zoneID = scanner.nextLine();
        System.out.print("Enter Space Number to update: ");
        int spaceNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        System.out.print("Enter new Space Type (Regular/Handicap/Compact/Electric): ");
        String spaceType = scanner.nextLine();
        System.out.print("Enter new Availability Status (Y/N): ");
        String availability = scanner.nextLine();

        String sql = "UPDATE Space SET spaceType = ?, availability = ? WHERE parkingLotID = ? AND zoneID = ? AND spaceNumber = ?";

        try {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, spaceType);
                pstmt.setString(2, availability);
                pstmt.setString(3, parkingLotID);
                pstmt.setString(4, zoneID);
                pstmt.setInt(5, spaceNumber);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Space has been updated.");
                    conn.commit(); // Commit the transaction
                } else {
                    System.out.println("Could not update space. Please check if the IDs and space number are correct.");
                    conn.rollback(); // Rollback in case of error
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    // Method to delete an existing space
    public void deleteSpace() {
        scanner.nextLine();
        System.out.print("Enter Parking Lot ID for the space to delete: ");
        String parkingLotID = scanner.nextLine();
        System.out.print("Enter Zone ID for the space to delete: ");
        String zoneID = scanner.nextLine();
        System.out.print("Enter Space Number to delete: ");
        int spaceNumber = scanner.nextInt();

        String sql = "DELETE FROM Space WHERE parkingLotID = ? AND zoneID = ? AND spaceNumber = ?";

        try {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, parkingLotID);
                pstmt.setString(2, zoneID);
                pstmt.setInt(3, spaceNumber);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Space has been deleted.");
                    conn.commit(); // Commit the transaction
                } else {
                    System.out.println("Could not delete space. Please check the IDs and space number are correct.");
                    conn.rollback(); // Rollback in case of error
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    // Method to view all spaces
    public void viewSpaces() {
        scanner.nextLine();
        String sql = "SELECT parkingLotID, zoneID, spaceNumber, spaceType, availability FROM Space";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Parking Lot ID: " + rs.getString("parkingLotID") +
                                   ", Zone ID: " + rs.getString("zoneID") +
                                   ", Space Number: " + rs.getInt("spaceNumber") +
                                   ", Space Type: " + rs.getString("spaceType") +
                                   ", Availability: " + rs.getString("availability"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void insertIntoAdmin(Connection conn) {

        scanner.nextLine();
        System.out.println("Enter userID for the new Admin: ");
	   	String userId = scanner.nextLine();
    	String sql = "INSERT INTO Admin (userID) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            System.out.println("Account created successfully.");
        } catch (SQLException e) {
			e.printStackTrace();
		}
    }

    private void insertIntoSecurityPersonnel(Connection conn) {
    	 scanner.nextLine();
    	 System.out.println("Enter userID for the new Security Personnel: ");
    	 String userId = scanner.nextLine();
        String sql = "INSERT INTO SecurityPersonnel (userID) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            System.out.println("Account created successfully.");
        } catch (SQLException e) {
			e.printStackTrace();
		}
    }

    
    // Method to add a new permit
    public void addPermit() {
        scanner.nextLine();
        
        System.out.print("Enter Parking Lot ID: ");
        String parkingLotID = scanner.nextLine();
        System.out.print("Enter Zone ID: ");
        String zoneID = scanner.nextLine();
        System.out.print("Enter Space Number: ");
        int spaceNumber = scanner.nextInt();
        scanner.nextLine(); 
    
        
        String checkSpaceSql = "SELECT availability FROM Space WHERE parkingLotID = ? AND zoneID = ? AND spaceNumber = ?";
        try (PreparedStatement checkSpaceStmt = conn.prepareStatement(checkSpaceSql)) {
            checkSpaceStmt.setString(1, parkingLotID);
            checkSpaceStmt.setString(2, zoneID);
            checkSpaceStmt.setInt(3, spaceNumber);
            ResultSet rs = checkSpaceStmt.executeQuery();
            if (rs.next()) {
                String availability = rs.getString("availability");
                if ("N".equals(availability)) {
                    System.out.println("The space is currently occupied. Cannot assign a permit.");
                    return;
                }
            } else {
                System.out.println("The specified space does not exist.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error checking space availability: " + e.getMessage());
            return;
        }
    
        
        System.out.print("Enter User ID: ");
        String userID = scanner.nextLine();
    
        String checkDisabilityStatusSql = "SELECT disabilityStatus FROM Driver WHERE userID = ?";
        String disabilityStatus = null;
        try (PreparedStatement checkDisabilityStatusStmt = conn.prepareStatement(checkDisabilityStatusSql)) {
            checkDisabilityStatusStmt.setString(1, userID);
            ResultSet rs = checkDisabilityStatusStmt.executeQuery();
            if (rs.next()) {
                disabilityStatus = rs.getString("disabilityStatus");
            }
        } catch (SQLException e) {
            System.out.println("Error checking disability status: " + e.getMessage());
            return;
        }
    
        if (disabilityStatus == null) {
            System.out.println("User's disability status could not be determined.");
            return;
        }
    
        String checkSpaceSuitabilitySql = "SELECT spaceType FROM Space WHERE parkingLotID = ? AND zoneID = ? AND spaceNumber = ?";
        try (PreparedStatement checkSpaceSuitabilityStmt = conn.prepareStatement(checkSpaceSuitabilitySql)) {
            checkSpaceSuitabilityStmt.setString(1, parkingLotID);
            checkSpaceSuitabilityStmt.setString(2, zoneID);
            checkSpaceSuitabilityStmt.setInt(3, spaceNumber);
            ResultSet rs = checkSpaceSuitabilityStmt.executeQuery();
            if (rs.next()) {
                String spaceType = rs.getString("spaceType");
                if ("D".equals(disabilityStatus) && !"Handicap".equals(spaceType) ||
                    !"D".equals(disabilityStatus) && "Handicap".equals(spaceType)) {
                    System.out.println("The space is not suitable for the user's disability status.");
                    return;
                }
            } else {
                System.out.println("The specified space does not exist.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error checking space suitability: " + e.getMessage());
            return;
        }
    
        System.out.print("Enter Permit ID: ");
        String permitID = scanner.nextLine();
        System.out.print("Enter Car License Number: ");
        String carLicenseNo = scanner.nextLine();
        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter Expiry Date (YYYY-MM-DD), leave blank if none: ");
        String expDate = scanner.nextLine();
        System.out.print("Enter Expiry Time (HH:MM:SS), leave blank if none: ");
        String expTime = scanner.nextLine();
        System.out.print("Enter Permit Type: ");
        String permitType = scanner.nextLine();
    
        String addPermitSql = "INSERT INTO Permit (permitID, carLicenseNo, parkingLotID, zoneID, spaceNumber, startDate, expDate, expTime, userID, permitType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      
        String updateSpaceSql = "UPDATE Space SET availability = 'N' WHERE parkingLotID = ? AND zoneID = ? AND spaceNumber = ?";
    
        try {
            conn.setAutoCommit(false); // Start transaction
    
            // Insert the permit
            try (PreparedStatement addPermitStmt = conn.prepareStatement(addPermitSql)) {
                addPermitStmt.setString(1, permitID);
                addPermitStmt.setString(2, carLicenseNo);
                addPermitStmt.setString(3, parkingLotID);
                addPermitStmt.setString(4, zoneID);
                addPermitStmt.setInt(5, spaceNumber);
                addPermitStmt.setDate(6, java.sql.Date.valueOf(startDate));
                if (!expDate.isEmpty()) {
                    addPermitStmt.setDate(7, java.sql.Date.valueOf(expDate));
                } else {
                    addPermitStmt.setNull(7, java.sql.Types.DATE);
                }
                if (!expTime.isEmpty()) {
                    addPermitStmt.setTime(8, java.sql.Time.valueOf(expTime));
                } else {
                    addPermitStmt.setNull(8, java.sql.Types.TIME);
                }
                addPermitStmt.setString(9, userID);
                addPermitStmt.setString(10, permitType);
                int affectedRows = addPermitStmt.executeUpdate();
    
                if (affectedRows == 0) {
                    throw new SQLException("Inserting permit failed, no rows affected.");
                }
            }
    
            try (PreparedStatement updateSpaceStmt = conn.prepareStatement(updateSpaceSql)) {
                updateSpaceStmt.setString(1, parkingLotID);
                updateSpaceStmt.setString(2, zoneID);
                updateSpaceStmt.setInt(3, spaceNumber);
                int affectedRows = updateSpaceStmt.executeUpdate();
    
                if (affectedRows == 0) {
                    throw new SQLException("Updating space availability failed, no rows affected.");
                }
            }
    
            // Commit the transaction if all operations were successful
            conn.commit();
            System.out.println("Permit added and space marked as occupied.");
    
        } catch (SQLException e) {
            System.out.println("Transaction failed: " + e.getMessage());
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println("Error during rollback: " + se.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println("Error resetting auto-commit: " + se.getMessage());
            }
        }
    }

    

    // Method to update an existing permit
    public void updatePermit() {
        scanner.nextLine();
        System.out.print("Enter Permit ID to update: ");
        String permitID = scanner.nextLine();
        System.out.print("Enter new Expiry Date (YYYY-MM-DD), leave blank if none: ");
        String expDate = scanner.nextLine();
        System.out.print("Enter new Expiry Time (HH:MM:SS), leave blank if none: ");
        String expTime = scanner.nextLine();

        String sql = "UPDATE Permit SET expDate = ?, expTime = ? WHERE permitID = ?";

        try {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                if (!expDate.isEmpty()) {
                    pstmt.setDate(1, java.sql.Date.valueOf(expDate));
                } else {
                    pstmt.setNull(1, java.sql.Types.DATE);
                }
                if (!expTime.isEmpty()) {
                    pstmt.setTime(2, java.sql.Time.valueOf(expTime));
                } else {
                    pstmt.setNull(2, java.sql.Types.TIME);
                }
                pstmt.setString(3, permitID);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Permit has been updated.");
                    conn.commit(); // Commit the transaction
                } else {
                    System.out.println("Could not update permit. Please check if the Permit ID is correct.");
                    conn.rollback(); // Rollback in case of error
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    // Method to delete an existing permit
    public void deletePermit() {
        scanner.nextLine();
        System.out.print("Enter Permit ID to delete: ");
        String permitID = scanner.nextLine();
    
        String findSpaceSql = "SELECT parkingLotID, zoneID, spaceNumber FROM Permit WHERE permitID = ?";

        String deletePermitSql = "DELETE FROM Permit WHERE permitID = ?";
        
        String updateSpaceSql = "UPDATE Space SET availability = 'Y' WHERE parkingLotID = ? AND zoneID = ? AND spaceNumber = ?";
    
        try {
            conn.setAutoCommit(false); // Start transaction
    
            String parkingLotID = null;
            String zoneID = null;
            int spaceNumber = -1;
    
            try (PreparedStatement findSpaceStmt = conn.prepareStatement(findSpaceSql)) {
                findSpaceStmt.setString(1, permitID);
                try (ResultSet rs = findSpaceStmt.executeQuery()) {
                    if (rs.next()) {
                        parkingLotID = rs.getString("parkingLotID");
                        zoneID = rs.getString("zoneID");
                        spaceNumber = rs.getInt("spaceNumber");
                    } else {
                        System.out.println("Permit not found.");
                        conn.rollback();
                        return;
                    }
                }
            }
    
            try (PreparedStatement deletePermitStmt = conn.prepareStatement(deletePermitSql)) {
                deletePermitStmt.setString(1, permitID);
                int affectedRows = deletePermitStmt.executeUpdate();
    
                if (affectedRows == 0) {
                    throw new SQLException("Deleting permit failed, no rows affected.");
                }
            }
    
            if (parkingLotID != null && zoneID != null && spaceNumber != -1) {
                try (PreparedStatement updateSpaceStmt = conn.prepareStatement(updateSpaceSql)) {
                    updateSpaceStmt.setString(1, parkingLotID);
                    updateSpaceStmt.setString(2, zoneID);
                    updateSpaceStmt.setInt(3, spaceNumber);
                    updateSpaceStmt.executeUpdate();
                }
            }
    
            // Commit the transaction
            conn.commit();
            System.out.println("Permit deleted and space marked as available.");
    
        } catch (SQLException e) {
            System.out.println("Transaction failed: " + e.getMessage());
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println("Error during rollback: " + se.getMessage());
            }
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println("Error resetting auto-commit: " + se.getMessage());
            }
        }
    }
    

    // Method to view permits
    public void viewPermit() {
        String sql = "SELECT * FROM Permit";
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (!rs.isBeforeFirst()) {
                System.out.println("No permits found.");
                return;
            }
    
            while (rs.next()) {
                System.out.println("Permit ID: " + rs.getString("permitID") +
                                   ", Car License No: " + rs.getString("carLicenseNo") +
                                   ", Parking Lot ID: " + rs.getString("parkingLotID") +
                                   ", Zone ID: " + rs.getString("zoneID") +
                                   ", Space Number: " + rs.getInt("spaceNumber") +
                                   ", Space Type: " + rs.getString("spaceType") +
                                   ", Start Date: " + rs.getDate("startDate") +
                                   ", Expiry Date: " + rs.getDate("expDate") +
                                   ", Expiry Time: " + rs.getTime("expTime") +
                                   ", User ID: " + rs.getString("userID") +
                                   ", Permit Type: " + rs.getString("permitType"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    

    // Method to add a new vehicle
    public void addVehicle() {
        scanner.nextLine();
        System.out.print("Enter Car License Number: ");
        String carLicenseNo = scanner.nextLine();
        System.out.print("Enter Manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Enter Model: ");
        String model = scanner.nextLine();
        System.out.print("Enter Color: ");
        String color = scanner.nextLine();
        System.out.print("Enter Year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        String sql = "INSERT INTO Vehicle (carLicenseNo, manufacturer, model, color, year) VALUES (?, ?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, carLicenseNo);
                pstmt.setString(2, manufacturer);
                pstmt.setString(3, model);
                pstmt.setString(4, color);
                pstmt.setInt(5, year);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("A new vehicle has been added.");
                    conn.commit(); // Commit the transaction
                } else {
                    System.out.println("A vehicle could not be added.");
                    conn.rollback(); // Rollback in case of error
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    // Method to update an existing vehicle
    public void updateVehicle() {
        scanner.nextLine();
        System.out.print("Enter Car License Number to update: ");
        String carLicenseNo = scanner.nextLine();
        System.out.print("Enter new Manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Enter new Model: ");
        String model = scanner.nextLine();
        System.out.print("Enter new Color: ");
        String color = scanner.nextLine();
        System.out.print("Enter new Year: ");
        int year = scanner.nextInt();
        scanner.nextLine(); 

        String sql = "UPDATE Vehicle SET manufacturer = ?, model = ?, color = ?, year = ? WHERE carLicenseNo = ?";

        try {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, manufacturer);
                pstmt.setString(2, model);
                pstmt.setString(3, color);
                pstmt.setInt(4, year);
                pstmt.setString(5, carLicenseNo);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Vehicle has been updated.");
                    conn.commit(); // Commit the transaction
                } else {
                    System.out.println("Could not update vehicle. Please check if the Car License Number is correct.");
                    conn.rollback(); // Rollback in case of error
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    // Method to delete an existing vehicle
    public void deleteVehicle() {
        scanner.nextLine();
        System.out.print("Enter Car License Number to delete: ");
        String carLicenseNo = scanner.nextLine();

        String sql = "DELETE FROM Vehicle WHERE carLicenseNo = ?";

        try {
            conn.setAutoCommit(false); // Start transaction
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, carLicenseNo);
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Vehicle has been deleted.");
                    conn.commit(); // Commit the transaction
                } else {
                    System.out.println("Could not delete vehicle. Please check the Car License Number is correct.");
                    conn.rollback(); // Rollback in case of error
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.setAutoCommit(true); // Reset auto-commit to true
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
        }
    }

    // Method to view an existing vehicle
    public void viewVehicle() {
        String sql = "SELECT * FROM Vehicle";
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (!rs.isBeforeFirst()) {
                System.out.println("No vehicles found.");
                return;
            }
    
            while (rs.next()) {
                System.out.println("Car License Number: " + rs.getString("carLicenseNo") +
                                   ", Manufacturer: " + rs.getString("manufacturer") +
                                   ", Model: " + rs.getString("model") +
                                   ", Color: " + rs.getString("color") +
                                   ", Year: " + rs.getInt("year"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    

    public void close() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
        if (scanner != null) {
            scanner.close();
        }
    }
    
}
