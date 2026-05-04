import java.util.List;
import java.util.Scanner;

public class StudentApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DatabaseManager dbManager = new DatabaseManager();

    public static void main(String[] args) {
        displayExistingStudents();

        Student newStudent = getStudentDetails();
        if (dbManager.addStudent(newStudent)) {
            System.out.println("Successfully added the student: " + newStudent);
        } else {
            System.out.println("Failed to add the student.");
        }

        scanner.close();
    }

    private static void displayExistingStudents() {
        System.out.println("Existing Students in the database:");
        List<Student> students = dbManager.getAllStudents();
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static Student getStudentDetails() {
        System.out.println("\nAdd a new student to the database.");
        System.out.print("Enter Student ID: ");
        String studentid = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lname = scanner.nextLine();
        System.out.print("Enter First Name: ");
        String fname = scanner.nextLine();
        System.out.print("Enter Middle Initial: ");
        String mi = scanner.nextLine();

        return new Student(studentid, lname, fname, mi);
    }
}

// PS D:\JavaDatabaseOOP> cd Database\DatabaseDemoOOP
// PS D:\JavaDatabaseOOP\DatabaseDemoOOP> javac -cp ".;lib\mysql-connector-j-9.2.0.jar" Student.java DatabaseManager.java StudentApp.java
// PS D:\JavaDatabaseOOP\DatabaseDemoOOP> java -cp ".;lib\mysql-connector-j-9.2.0.jar" StudentApp                           
// Existing Students in the database:
// Student ID: S001, Last Name: Jamandre, First Name: Markh, MI: B
// Student ID: S001, Last Name: Jamandre, First Name: Ma. Olive, MI: B
// Student ID: S003, Last Name: De la Cruz, First Name: Juan, MI: J

// Add a new student to the database.
// Enter Student ID: S004
// Enter Last Name: Doe 
// Enter First Name: John
// Enter Middle Initial: P
// Successfully added the student: Student ID: S004, Last Name: Doe, First Name: John, MI: P
// PS D:\Java2Source\OOP\Database\DatabaseDemoOOP> java -cp ".;lib\mysql-connector-j-9.2.0.jar" StudentApp
// Existing Students in the database:
// Student ID: S001, Last Name: Jamandre, First Name: Markh, MI: B
// Student ID: S001, Last Name: Jamandre, First Name: Ma. Olive, MI: B
// Student ID: S003, Last Name: De la Cruz, First Name: Juan, MI: J
// Student ID: S004, Last Name: Doe, First Name: John, MI: P

// Add a new student to the database.
// Enter Student ID: 

