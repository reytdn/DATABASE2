import java.sql.*;
import java.util.Scanner;

public class AddRecordOptimize {
    private static final String URL = "jdbc:mysql://localhost:3306/javadb";
    
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final String SELECT_QUERY = "SELECT studentid, lname, fname, mi FROM student";
    private static final String INSERT_QUERY = "INSERT INTO student (studentid, lname, fname, mi) VALUES (?, ?, ?, ?)";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            boolean exitProgram = false;

            while (!exitProgram) {
                System.out.println("\nMenu:");
                System.out.println("1. Add a new student");
                System.out.println("2. Show all records");
                System.out.println("3. Exit the program");
                System.out.print("Enter your choice (1/2/3): ");
                int choice = scanner.nextInt();
                scanner.nextLine();  

                switch (choice) {
                    case 1:
                        System.out.println("\nAdd a new student to the database.");
                        System.out.print("Enter Student ID: ");
                        String studentid = scanner.nextLine();
                        System.out.print("Enter Last Name: ");
                        String lname = scanner.nextLine();
                        System.out.print("Enter First Name: ");
                        String fname = scanner.nextLine();
                        System.out.print("Enter Middle Initial: ");
                        String mi = scanner.nextLine();

                        insertStudent(connection, studentid, lname, fname, mi);
                        break;

                    case 2:
                        displayAllRecords(connection);
                        break;

                    case 3:
                        exitProgram = true;
                        System.out.println("Exiting the program...");
                        break;

                    default:
                        System.out.println("Invalid choice! Please enter 1, 2, or 3.");
                }
            }

            scanner.close();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }

    private static void displayAllRecords(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY);

        System.out.println("Existing Students in the database:");
        while (resultSet.next()) {
            String studentid = resultSet.getString("studentid");
            String lname = resultSet.getString("lname");
            String fname = resultSet.getString("fname");
            String mi = resultSet.getString("mi");
            System.out.println("Student ID: " + studentid + ", Last Name: " + lname + ", First Name: " + fname + ", MI: " + mi);
        }

        resultSet.close();
        statement.close();
    }

    private static void insertStudent(Connection connection, String studentid, String lname, String fname, String mi) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY);
        preparedStatement.setString(1, studentid);
        preparedStatement.setString(2, lname);
        preparedStatement.setString(3, fname);
        preparedStatement.setString(4, mi);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Successfully added the student: " + studentid + ", " + lname + ", " + fname + " " + mi);
        } else {
            System.out.println("Failed to add the student.");
        }

        preparedStatement.close();
    }
}
