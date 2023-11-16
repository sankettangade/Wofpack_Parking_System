import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Report {

    private Connection conn;

    // Constructor to initialize the connection
    public Report(Connection conn) {
        this.conn = conn;
    }

    // Function to get lot citation count for a specified time range (default: Monthly)
    public List<Tuple<String, String>> getLotCitationCount(String timeRange) {
        if (timeRange == null || timeRange.isEmpty()) {
            timeRange = "Monthly"; // Default to Monthly if time range is not selected
        }

        List<Tuple<String, String>> lotCitationCountList = new ArrayList<>();

        String sql = "SELECT parkingLotID, zone FROM Citations WHERE citationDate >= ?";
        
        // Assuming citationDate is a date field in the database

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set the date condition based on the time range (e.g., last month)
            // Adjust this part based on the actual date comparison logic you want
            pstmt.setDate(1, calculateStartDateBasedOnTimeRange(timeRange));

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                lotCitationCountList.add(new Tuple<>(rs.getString("parkingLotID"), rs.getString("zone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lotCitationCountList;
    }

    // Function to get zones for all parking lots
    public List<Tuple<String, String>> getZones() {
        List<Tuple<String, String>> zoneList = new ArrayList<>();

        String sql = "SELECT parkingLotID, zone FROM ParkingLots";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                zoneList.add(new Tuple<>(rs.getString("parkingLotID"), rs.getString("zone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return zoneList;
    }

    // Function to get a list of car licenses that are in violation
    public List<String> getViolationCarCount() {
        List<String> violationCarList = new ArrayList<>();

        String sql = "SELECT DISTINCT carLicenseNo FROM Citations WHERE paymentStatus = 'Unpaid'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                violationCarList.add(rs.getString("carLicenseNo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return violationCarList;
    }

    // Function to get permitted employees for a specific parking lot
    public List<String> getPermittedEmployees(String parkingLotID) {
        if (parkingLotID == null || parkingLotID.isEmpty()) {
            throw new IllegalArgumentException("Parking Lot ID cannot be null or empty.");
        }

        List<String> permittedEmployeesList = new ArrayList<>();

        String sql = "SELECT DISTINCT driverID FROM Permits WHERE parkingLotID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, parkingLotID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                permittedEmployeesList.add(rs.getString("driverID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return permittedEmployeesList;
    }

    // Function to get permit information for a specific driver
    public Tuple<String, String> getPermitInformation(String driverID) {
        if (driverID == null || driverID.isEmpty()) {
            throw new IllegalArgumentException("Driver ID cannot be null or empty.");
        }

        String sql = "SELECT parkingLotID, expirationDate FROM Permits WHERE driverID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, driverID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Tuple<>(rs.getString("parkingLotID"), rs.getString("expirationDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // If no permit information found for the driver
        throw new IllegalArgumentException("No permit information found for driver ID: " + driverID);
    }

    // Function to get available spaces for parking in a specific parking lot and space type
    public List<String> getAvailableSpaces(String parkingLotID, String spaceType) {
        if (parkingLotID == null || parkingLotID.isEmpty() || spaceType == null || spaceType.isEmpty()) {
            throw new IllegalArgumentException("Parking Lot ID and Space Type cannot be null or empty.");
        }

        List<String> availableSpacesList = new ArrayList<>();

        String sql = "SELECT spaceID FROM ParkingSpaces WHERE parkingLotID = ? AND spaceType = ? AND isOccupied = false";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, parkingLotID);
            pstmt.setString(2, spaceType);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                availableSpacesList.add(rs.getString("spaceID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableSpacesList;
    }

    // Helper function to calculate the start date based on the selected time range
    private Date calculateStartDateBasedOnTimeRange(String timeRange) {
        // Implement the logic to calculate the start date based on the time range (e.g., last month)
        // You may need to adjust this part based on the actual date comparison logic you want
        // For simplicity, returning the current date here
        return Date.valueOf(LocalDate.now());
    }

    // Tuple class for holding pairs of values
    public static class Tuple<T, U> {
        public final T first;
        public final U second;

        public Tuple(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }
}
