import java.sql.*;
import java.util.Scanner;

public class Add_Record {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/javadb";
        String user = "root";
        String password = "";

        String selectQuery = "SELECT studentid, lname, fname, mi FROM student";
        String insertQuery = "INSERT INTO student (studentid, lname, fname, mi) VALUES (?, ?, ?, ?)";

        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            System.out.println("Existing Students in the database:");
            while (resultSet.next()) {   
                String studentid = resultSet.getString("studentid");
                String lname = resultSet.getString("lname");
                String fname = resultSet.getString("fname");
                String mi = resultSet.getString("mi");
                System.out.println("Student ID: " + studentid + ", Last Name: " + lname + ", First Name: " + fname + ", MI: " + mi);
            }

            System.out.println("\nAdd a new student to the database.");
            System.out.print("Enter Student ID: ");
            String studentid = scanner.nextLine();
            System.out.print("Enter Last Name: ");
            String lname = scanner.nextLine();
            System.out.print("Enter First Name: ");
            String fname = scanner.nextLine();
            System.out.print("Enter Middle Initial: ");
            String mi = scanner.nextLine();

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
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
            resultSet.close();
            statement.close();
            connection.close();
            scanner.close();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}

