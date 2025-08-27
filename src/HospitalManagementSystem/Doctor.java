package HospitalManagementSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    //View ALL Doctors
    public void viewDoctors() {
        String query = "SELECT * from doctors";
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(query);
            ResultSet resultSet = prepareStatement.executeQuery();

            System.out.println("Doctors: ");
            System.out.println("+-----------+-----------------+----------------------+");
            System.out.println("| Doctor Id | Name            | Specialization       |");
            System.out.println("+-----------+-----------------+----------------------+");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");

                // %-Nd = left-align, fixed width
                System.out.printf("| %-9d | %-15s | %-20s |\n", id, name, specialization);
            }

            System.out.println("+-----------+-----------------+----------------------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Check if Doctor exists by ID
    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM Doctors WHERE id=?";
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
