package lk.ijse.repository;

import lk.ijse.Model.PetOwner;
import lk.ijse.dbConnection.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetOwnerRepo {
    public static boolean save(PetOwner petOwner) throws SQLException {
        String sql = "INSERT INTO PetOwner VALUES(?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, petOwner.getPetOwId());
        pstm.setString(2, petOwner.getName());
        pstm.setString(3, petOwner.getContactNo());

        return pstm.executeUpdate() > 0;
    }

    public static PetOwner searchById(String id) throws SQLException {
        String sql = "SELECT * FROM PetOwner WHERE petOwId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            String petOwId = rs.getString(1);
            String name = rs.getString(2);
            String contactNo = rs.getString(3);

            return new PetOwner(petOwId, name, contactNo);
        }
        return null;
    }

    public static boolean update(PetOwner petOwner) throws SQLException {
        String sql = "UPDATE PetOwner SET name = ?, contactNo = ? WHERE petOwId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, petOwner.getName());
        pstm.setString(2, petOwner.getContactNo());
        pstm.setString(3, petOwner.getPetOwId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM PetOwner WHERE petOwId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public static List<PetOwner> getAll() throws SQLException {
        String sql = "SELECT * FROM PetOwner";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<PetOwner> petOwnerList = new ArrayList<>();
        while (resultSet.next()) {
            String petOwId = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contactNo = resultSet.getString(3);
            PetOwner petOwner = new PetOwner(petOwId, name, contactNo);
            petOwnerList.add(petOwner);
        }
        return petOwnerList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT petOwId FROM PetOwner";
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