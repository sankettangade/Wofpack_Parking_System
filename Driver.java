import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.util.Scanner;

public class Driver {
    private Connection conn;
    private Properties prop;
    private String userID;
    private Scanner scanner;

    public Driver(String userID) {
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
            conn.setAutoCommit(false);
            System.out.println("Connected to the database successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void show_welcome_page() {
        int choice = 0;
        do {
            System.out.println("\nWelcome, Driver!");
            System.out.println("-----------------------------------------------");
            System.out.println("1. Add/Update/Delete/View Driver");
            System.out.println("2. Add/Update/Delete/View Vehicle");
            System.out.println("3. Pay/View/Appeal Citations");
            System.out.println("4. View Permit Info");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    driverMenu(userID);
                    break;
                case 2:
                    vehicleMenu(userID);
                    break;
                case 3:
                    citationsMenu(userID);
                    break;
                case 4:
                    viewPermitInfo(userID);
                    break;
                case 5:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }


    private void driverMenu(String userID) {
        int choice = 0;
        do {
        
        System.out.println("1. Add Driver Info");
        System.out.println("2. Update Driver Info");
        System.out.println("3. Delete Driver INfo");
        System.out.println("4. View Driver Info");
        System.out.println("5. Exit");
        // Call respective methods based on user choice
        choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addDriverInfo();
                    break;
                case 2:
                    updateDriverInfo(userID);
                    break;
                case 3:
                    deleteDriverInfo(userID);
                    break;
                case 4:
                    viewDriverInfo(userID);
                    break;
                case 5:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }


    private void vehicleMenu(String userID) {
        int choice = 0;
        do {
        
        System.out.println("1. Add Vehicle");
        System.out.println("2. Update Vehicle");
        System.out.println("3. Delete Vehicle");
        System.out.println("4. View Vehicle info");
        System.out.println("5. Exit");
        // Call respective methods based on user choice
        choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addVehicle(userID);
                    break;
                case 2:
                    updateVehicle();
                    break;
                case 3:
                    deleteVehicle();
                    break;
                case 4:
                    viewVehicle(userID);
                    break;
                case 5:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
        
    }


    private void citationsMenu(String userID) {
        int choice = 0;
        do {
        
        System.out.println("1. View Citations");
        System.out.println("2. Pay Citation fee");
        System.out.println("3. Appeal against a citation");
        System.out.println("4. Exit");
        // Call respective methods based on user choice
        choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewCitations(userID);
                    break;
                case 2:
                    payCitations(userID);
                    break;
                case 3:
                    appealCitations(userID);
                    break;
                case 4:
                    System.out.println("Exiting. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }


    public void addDriverInfo() {
        scanner.nextLine();
        System.out.print("Enter Driver's userID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter Driver's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter status: ");
        String status = scanner.nextLine();
        System.out.print("Enter disability status: ");
        String disability_status = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String sql = "INSERT INTO User (userID, name, status, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, name);
            pstmt.setString(3, status);
            pstmt.setString(4, password);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("A new user has been added.");
                conn.commit();
            } else {
                System.out.println("A user could not be added.");
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

        String insert_sql = "INSERT INTO Driver (userID, disabilityStatus) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(insert_sql)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, disability_status);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("A new driver has been added.");
                conn.commit();
            } else {
                System.out.println("A driver could not be added.");
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


    public void updateDriverInfo(String userID) {
        scanner.nextLine();
        System.out.print("Enter new Driver Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();
        System.out.print("Enter new password: ");
        String password = scanner.nextLine();
        System.out.print("Enter new disability status: ");
        String disability_status = scanner.nextLine();

        String update_sql = "UPDATE User SET name = ?, status = ?, password = ? WHERE userID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(update_sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, status);
            pstmt.setString(3, password);
            pstmt.setString(4, userID);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User info has been updated.");
                conn.commit();
            } else {
                System.out.println("Could not update user info. Please check if the ID is correct.");
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

        String sql = "UPDATE Driver SET disabilityStatus = ? WHERE userID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, disability_status);
            pstmt.setString(2, userID);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Driver info has been updated.");
                conn.commit();
            } else {
                System.out.println("Could not update driver info. Please check if the ID is correct.");
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


    public void deleteDriverInfo(String userID) {
        String sql = "DELETE FROM Driver WHERE userID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Driver "+ userID +" has been deleted.");
                conn.commit();
            } else {
                System.out.println("Could not delete driver info. Please check if the ID is correct or login correctly.");
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


    public void viewDriverInfo(String userID) {
        String sql_1 = "SELECT * FROM User WHERE userID = ?";

        try (PreparedStatement pstmt_1 = conn.prepareStatement(sql_1)) {
            pstmt_1.setString(1, userID);
            ResultSet rs_1 = pstmt_1.executeQuery();

            if (rs_1.next()) {
                String sql_2 = "SELECT * FROM Driver WHERE userID = ?";

                try (PreparedStatement pstmt_2 = conn.prepareStatement(sql_2)) {
                    pstmt_2.setString(1, userID);
                    ResultSet rs_2 = pstmt_2.executeQuery();

                    if (rs_2.next()) {
                        System.out.println("User ID: " + rs_2.getString("useriD") +
                                "\nName: " + rs_1.getString("name") +
                                "\nStatus: " + rs_1.getString("status") +
                                "\nDisability Status: " + rs_2.getString("disabilityStatus") +
                                "\nPassword: " + rs_1.getString("password"));
                    } else {
                        System.out.println("Driver with user ID " + userID + " not found.");
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            } else {
                System.out.println("User with user ID " + userID + " not found.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public void addVehicle(String userID) {
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
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        }

        String sql_1 = "INSERT INTO registered (userID, carLicenseNo) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql_1)) {
            pstmt.setString(1, userID);
            pstmt.setString(2, carLicenseNo);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("A new vehicle has been registered.");
                conn.commit(); // Commit the transaction
            } else {
                System.out.println("A vehicle could not be registered.");
                conn.rollback(); // Rollback in case of error
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                conn.rollback(); // Rollback in case of any error
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
        scanner.nextLine(); // Consume the newline

        String sql = "UPDATE Vehicle SET manufacturer = ?, model = ?, color = ?, year = ? WHERE carLicenseNo = ?";

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                conn.rollback(); // Rollback in case of any error
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

        String sql_1 = "DELETE FROM registered WHERE carLicenseNo = ?";
        
        try (PreparedStatement pstmt_1 = conn.prepareStatement(sql_1)) {
            pstmt_1.setString(1, carLicenseNo);
            int affectedRows = pstmt_1.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Vehicle's registration has been removed.");
                String sql_2 = "DELETE FROM Vehicle WHERE carLicenseNo = ?";
                try (PreparedStatement pstmt_2 = conn.prepareStatement(sql_2)) {
                    pstmt_2.setString(1, carLicenseNo);
                    int affectedRows_2 = pstmt_2.executeUpdate();

                    if (affectedRows_2 > 0) {
                        System.out.println("Vehicle has been removed.");
                        conn.commit(); // Commit the transaction
		            } else {
		                System.out.println("Could not delete vehicle. Please check the Car License Number is correct.");
		                conn.rollback(); // Rollback in case of error
		            }
                } catch (SQLException e) {
                    try {
                        conn.rollback(); // Rollback in case of any error
                    } catch (SQLException se) {
                        System.out.println(se.getMessage());
                    }
                    System.out.println(e.getMessage());
                } 
            } else {
            	System.out.println("Could not de-register the vehicle. Please check the Car License Number is correct.");
                conn.rollback(); // Rollback in case of error
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback in case of any error
            } catch (SQLException se) {
                System.out.println(se.getMessage());
            }
            System.out.println(e.getMessage());
        } 
    }


    // Method to view user's existing vehicle
    public void viewVehicle(String userID) {
        String sql = "SELECT * FROM Vehicle as v JOIN registered as r ON v.carLicenseNo = r.carLicenseNo WHERE r.userID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();
        
            // Check if at least one result has been returned
            if (!rs.isBeforeFirst()) {
                System.out.println("No vehicles found.");
                return;
            }
    
            while (rs.next()) {
                System.out.println("Car License Number: " + rs.getString("carLicenseNo") +
                                "\nManufacturer: " + rs.getString("manufacturer") +
                                "\nModel: " + rs.getString("model") +
                                "\nColor: " + rs.getString("color") +
                                "\nYear: " + rs.getInt("year"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewPermitInfo(String userID) {
        String sql = "SELECT * FROM Permit WHERE userID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("User ID: " + rs.getString("userID") +
                        "\nPermit ID: " + rs.getString("permitID") +
                        "\nCar License No: " + rs.getString("carLicenseNo") +
                        "\nParking Lot ID: " + rs.getString("parkingLotID") +
                        "\nZone ID: " + rs.getString("zoneID") +
                        "\spaceNumber: " + rs.getString("spaceNumber") +
                        "\nspaceType: " + rs.getString("spaceType") +
                        "\nStart Date: " + rs.getString("startDate") +
                        "\nExp Date : " + rs.getString("expDate") +
                        "\nExp TIme: " + rs.getString("expTime") +
                        "\nPermit Type: " + rs.getString("permitType") 
                        );
            } else {
                System.out.println("Permit for user ID " + userID + " does not exist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public void viewCitations(String userID) {
        String sql_1 = "SELECT * FROM registered WHERE userID = ?";

        try (PreparedStatement pstmt_1 = conn.prepareStatement(sql_1)) {
            pstmt_1.setString(1, userID);
            ResultSet rs = pstmt_1.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No vehicles found for this user " + userID);
                return;
            }

            while (rs.next()) {
                String carLicenseNo = rs.getString("carLicenseNo");
                String sql_2 = "SELECT * FROM Citations WHERE carLicenseNo = ?";
                try (PreparedStatement pstmt_2 = conn.prepareStatement(sql_2)) {
                    pstmt_2.setString(1, carLicenseNo);
                    ResultSet rs_1 = pstmt_2.executeQuery();

                    if (!rs_1.isBeforeFirst()) {
                        System.out.println("No citations this vehicle " + carLicenseNo);
                        return;
                    }

                    while (rs_1.next()) {
                        System.out.println("Citation Number: " + rs_1.getString("citationNumber") +
                                "\nCar License No: " + rs_1.getString("carLicenseNo") +
                                "\nParking Lot ID: " + rs_1.getString("parkingLotID") +
                                "\nCitation Date: " + rs_1.getString("citationDate") +
                                "\nCitation Time: " + rs_1.getString("citationTime") +
                                "\nCitation Date: " + rs_1.getString("citationDate") +
                                "\nCategory: " + rs_1.getString("category") +
                                "\nFee: " + rs_1.getString("fee") +
                                "\nPayment Status: " + rs_1.getString("paymentStatus") +
                                "\nAppeal: " + rs_1.getString("appeal")
                                );
                    }
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public void payCitations(String userID) {
        scanner.nextLine();
        System.out.print("Enter Vehicle's License Number: ");
        String carLicenseNo = scanner.nextLine();

        String sql_1 = "SELECT * FROM Citations WHERE carLicenseNo = ?";
        try (PreparedStatement pstmt_1 = conn.prepareStatement(sql_1)) {
            pstmt_1.setString(1, carLicenseNo);
            ResultSet rs_1 = pstmt_1.executeQuery();

            if (!rs_1.isBeforeFirst()) {
                System.out.println("No citations against this vehicle " + carLicenseNo);
                return;
            }

            while (rs_1.next()) {
            	System.out.println("Payment Status: " + rs_1.getString("paymentStatus"));
                String paymentStatus = rs_1.getString("paymentStatus");
                if (paymentStatus.equals("DUE")) {
                    paymentStatus = "PAID";
                    String sql_2 = "UPDATE Citations SET paymentStatus = ? WHERE carLicenseNo = ?";
                    try (PreparedStatement pstmt_2 = conn.prepareStatement(sql_2)) {
                        pstmt_2.setString(1, paymentStatus);
                        pstmt_2.setString(2, carLicenseNo);
                        int affectedRows = pstmt_2.executeUpdate();

                        if (affectedRows > 0) {
                            System.out.println("Citation has been paid successfully.");
                            conn.commit(); // Commit the transaction
                        } else {
                            System.out.println("Could not pay for the citation. Please check if the Car License Number is correct.");
                            conn.rollback(); // Rollback in case of error
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        try {
                            conn.rollback();
                        } catch (SQLException rollbackException) {
                            rollbackException.printStackTrace();
                        }
                    }   
                } else {
                    System.out.println("Citation has already been paid previously.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void appealCitations(String userID) {
        scanner.nextLine();
        System.out.print("Enter Vehicle's License Number: ");
        String carLicenseNo = scanner.nextLine();

        String sql_1 = "SELECT * FROM Citations WHERE carLicenseNo = ?";
        try (PreparedStatement pstmt_1 = conn.prepareStatement(sql_1)) {
            pstmt_1.setString(1, carLicenseNo);
            ResultSet rs_1 = pstmt_1.executeQuery();

            if (!rs_1.isBeforeFirst()) {
                System.out.println("No citations against this vehicle " + carLicenseNo);
                return;
            }

            while (rs_1.next()) {
            	System.out.println("Appeal: " + rs_1.getString("appeal"));
                String appeal = rs_1.getString("appeal");
                if (appeal.equals("no")) {
                    appeal = "yes";
                    String sql_2 = "UPDATE Citations SET appeal = ? WHERE carLicenseNo = ?";
                    try (PreparedStatement pstmt_2 = conn.prepareStatement(sql_2)) {
                        pstmt_2.setString(1, appeal);
                        pstmt_2.setString(2, carLicenseNo);
                        int affectedRows = pstmt_2.executeUpdate();

                        if (affectedRows > 0) {
                            System.out.println("Appeal against the citation has been made.");
                            conn.commit(); // Commit the transaction
                        } else {
                            System.out.println("Could not appeal against the citation. Please check if the Car License Number is correct.");
                            conn.rollback(); // Rollback in case of error
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                        try {
                            conn.rollback();
                        } catch (SQLException rollbackException) {
                            rollbackException.printStackTrace();
                        }
                    }   
                } else {
                    System.out.println("Appeal against this citation already exists.");
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}