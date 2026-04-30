import java.sql.*;
import java.util.Scanner;

public class JavaDatabaseDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/javadb";
        String user = "root";
        String password = "";
        
        String insertQuery = "INSERT INTO student (studentid, lname, fname, mi) VALUES (?, ?, ?, ?)";
        String selectQuery = "SELECT studentid, lname, fname, mi FROM student";
        String updateQuery = "UPDATE student SET lname = ?, fname = ?, mi = ? WHERE studentid = ?";
        String deleteQuery = "DELETE FROM student WHERE studentid = ?";
        String searchQuery = "SELECT studentid, lname, fname, mi FROM student WHERE studentid = ?";

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("Main Menu");
            System.out.println("1. ADD");
            System.out.println("2. DISPLAY");
            System.out.println("3. UPDATE");
            System.out.println("4. DELETE");
            System.out.println("5. SEARCH");
            System.out.println("6. EXIT");
            System.out.println();

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if(choice == 1){
                System.out.println();
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


                } catch (ClassNotFoundException e) {
                    System.out.println("MySQL JDBC Driver not found!");
                    e.printStackTrace();
                } catch (SQLException e) {
                    System.out.println("Connection failed!");
                    e.printStackTrace();
                }
            }
            

            if(choice == 2){
                System.out.println();
                String query = "SELECT id, lname, fname, mi FROM student";
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, user, password);
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String lname = resultSet.getString("lname");
                        String fname = resultSet.getString("fname");
                        String mi = resultSet.getString("mi");
                        System.out.println("ID: " + id + ", Last Name: " + lname + ", First Name: " + fname  + ", MI: " + mi);
                    }

                    resultSet.close();
                    statement.close();
                    connection.close();

                } catch (ClassNotFoundException e) {
                    System.out.println("MySQL JDBC Driver not found!");
                    e.printStackTrace();
                } catch (SQLException e) {
                    System.out.println("Connection failed!");
                    e.printStackTrace();
                }
            }

            if(choice == 3){
                System.out.println();
                System.out.println("UPDATE Student");
                System.out.print("Enter Student ID to update: ");
                String studentid = scanner.nextLine();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, user, password);
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM student WHERE studentid = '" + studentid + "'");

                    if (resultSet.next()) {
                        System.out.println("Current Details:");
                        System.out.println("Student ID: " + resultSet.getString("studentid"));
                        System.out.println("Last Name: " + resultSet.getString("lname"));
                        System.out.println("First Name: " + resultSet.getString("fname"));
                        System.out.println("Middle Initial: " + resultSet.getString("mi"));

                        System.out.println("\nEnter new details:");
                        System.out.print("Enter Last Name: ");
                        String lname = scanner.nextLine();
                        System.out.print("Enter First Name: ");
                        String fname = scanner.nextLine();
                        System.out.print("Enter Middle Initial: ");
                        String mi = scanner.nextLine();

                        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                        preparedStatement.setString(1, lname);
                        preparedStatement.setString(2, fname);
                        preparedStatement.setString(3, mi);
                        preparedStatement.setString(4, studentid);

                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Successfully updated the student.");
                        } else {
                            System.out.println("Failed to update the student.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }

                    resultSet.close();
                    statement.close();
                    connection.close();

                } catch (ClassNotFoundException e) {
                    System.out.println("MySQL JDBC Driver not found!");
                    e.printStackTrace();
                } catch (SQLException e) {
                    System.out.println("Connection failed!");
                    e.printStackTrace();
                }
            }

            if(choice == 4){
                System.out.println();
                System.out.println("DELETE Student");
                System.out.print("Enter Student ID to delete: ");
                String studentid = scanner.nextLine();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, user, password);
                    PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                    preparedStatement.setString(1, studentid);

                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Successfully deleted the student.");
                    } else {
                        System.out.println("Failed to delete the student. Student ID may not exist.");
                    }

                    preparedStatement.close();
                    connection.close();

                } catch (ClassNotFoundException e) {
                    System.out.println("MySQL JDBC Driver not found!");
                    e.printStackTrace();
                } catch (SQLException e) {
                    System.out.println("Connection failed!");
                    e.printStackTrace();
                }
            }
            
            if(choice == 5){
                System.out.println();
                System.out.println("SEARCH Student");
                System.out.print("Enter Student ID to search: ");
                String studentid = scanner.nextLine();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection(url, user, password);
                    PreparedStatement preparedStatement = connection.prepareStatement(searchQuery);
                    preparedStatement.setString(1, studentid);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        System.out.println("Student found:");
                        System.out.println("Student ID: " + resultSet.getString("studentid"));
                        System.out.println("Last Name: " + resultSet.getString("lname"));
                        System.out.println("First Name: " + resultSet.getString("fname"));
                        System.out.println("Middle Initial: " + resultSet.getString("mi"));
                    } else {
                        System.out.println("Student not found.");
                    }

                    resultSet.close();
                    preparedStatement.close();
                    connection.close();

                } catch (ClassNotFoundException e) {
                    System.out.println("MySQL JDBC Driver not found!");
                    e.printStackTrace();
                } catch (SQLException e) {
                    System.out.println("Connection failed!");
                    e.printStackTrace();
                }
            }

            if(choice == 6){
                System.out.println();
                System.out.println("GOOD BYE!");
                System.out.println();
                break;

            }
        }
        scanner.close(); 
    }
}
