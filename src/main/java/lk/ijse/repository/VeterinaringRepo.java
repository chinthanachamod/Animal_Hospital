package lk.ijse.repository;

import lk.ijse.Model.Veterinarian;
import lk.ijse.dbConnection.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeterinaringRepo {
    public static boolean save(Veterinarian veterinarian) throws SQLException {
        String sql = "INSERT INTO Veterinarian VALUES(?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, veterinarian.getVetId());
        pstm.setString(2, veterinarian.getName());
        pstm.setInt(3, veterinarian.getYrsOfExperience());

        return pstm.executeUpdate() > 0;
    }

    public static Veterinarian searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Veterinarian WHERE vetId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            String vetId = rs.getString(1);
            String name = rs.getString(2);
            int yrsOfExperience = rs.getInt(3);

            return new Veterinarian(vetId, name, yrsOfExperience);
        }
        return null;
    }

    public static boolean update(Veterinarian veterinarian) throws SQLException {
        String sql = "UPDATE Veterinarian SET name = ?, yrsOfExperience = ? WHERE vetId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, veterinarian.getName());
        pstm.setInt(2, veterinarian.getYrsOfExperience());
        pstm.setString(3, veterinarian.getVetId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Veterinarian WHERE vetId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public static List<Veterinarian> getAll() throws SQLException {
        String sql = "SELECT * FROM Veterinarian";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<Veterinarian> veterinarianList = new ArrayList<>();
        while (resultSet.next()) {
            String vetId = resultSet.getString(1);
            String name = resultSet.getString(2);
            int yrsOfExperience = resultSet.getInt(3);
            Veterinarian veterinarian = new Veterinarian(vetId, name, yrsOfExperience);
            veterinarianList.add(veterinarian);
        }
        return veterinarianList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT vetId FROM Veterinarian";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        List<String> idList = new ArrayList<>();
        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }
}