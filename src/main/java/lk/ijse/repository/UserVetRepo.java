package lk.ijse.repository;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import lk.ijse.dbConnection.DbConnection;

public class UserVetRepo {
    public static boolean check(String username, String password) throws SQLException {
        try {
            System.out.println("helloooo");
            String sql = "SELECT yrsOfExperience FROM Veterinarian WHERE name = ?";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("result Set in");
                String dbPassword = resultSet.getString("yrsOfExperience");
                return dbPassword.equals(password);
            } else {

                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

