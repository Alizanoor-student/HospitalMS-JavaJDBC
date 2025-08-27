package HospitalManagementSystem;
import  java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // Add Patient
    public void addPatient() {
        System.out.println("Enter Patient Name: ");
        String name = scanner.next();
        System.out.println("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender: ");
        String gender = scanner.next();
        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully!!");
            } else {
                System.out.println("Failed to add Patient!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //View ALL Patients
    public void viewPatients() {
        String query = "SELECT * FROM patients ORDER BY id"; // ensures proper sequence
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet resultSet = prepareStatement.executeQuery();

            System.out.println("Patients:");
            System.out.println("+------------+------------+-----+--------+");
            System.out.println("| Patient Id | Name       | Age | Gender |");
            System.out.println("+------------+------------+-----+--------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                // Proper spacing for alignment
                System.out.printf("| %-10d | %-10s | %-3d | %-6s |\n", id, name, age, gender);
            }

            System.out.println("+------------+------------+-----+--------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Check if Patient exists by ID
    public boolean getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;//default return if exception occurs
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;//ensures method always returns
    }
}