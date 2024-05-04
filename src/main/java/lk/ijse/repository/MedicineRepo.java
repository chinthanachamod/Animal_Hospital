package lk.ijse.repository;

import lk.ijse.Model.Medicine;
import lk.ijse.Model.PetOwner;
import lk.ijse.dbConnection.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class  MedicineRepo{
    public static boolean save(Medicine medicine) throws SQLException {
        String sql = "INSERT INTO Medicine VALUES(?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, medicine.getMedId());
        pstm.setString(2, medicine.getDescription());
        pstm.setString(3, medicine.getDescription());
        pstm.setString(4, String.valueOf(medicine.getPrice()));

        return pstm.executeUpdate() > 0;
    }

    public static Medicine searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Medicine WHERE medId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            String medId = rs.getString(1);
            String description = rs.getString(2);
            String qty = rs.getString(3);
            String price = rs.getString(4);

            return new Medicine(medId, description, qty, price);
        }
        return null;
    }

    public static boolean update(Medicine medicine) throws SQLException {
        String sql = "UPDATE Medicine SET description = ?, qty = ?, price = ? WHERE medId = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, medicine.getDescription());
            pstm.setString(2, medicine.getQty());
            pstm.setString(3, String.valueOf(medicine.getPrice()));
            pstm.setString(4, medicine.getMedId());

            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Medicine WHERE medId = ?";
        try (Connection connection = DbConnection.getInstance().getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, id);
            return pstm.executeUpdate() > 0;
        }
    }

    public static List<Medicine> getAll() throws SQLException {
        String sql = "SELECT * FROM Medicine";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<Medicine> medicineList = new ArrayList<>();
        while (resultSet.next()) {
            String medID = resultSet.getString(1);
            String description = resultSet.getString(2);
            String qty = resultSet.getString(3);
            String price = resultSet.getString(4);
            PetOwner Medicine = new Medicine(medID, description, qty, price);
            medicineList.add(medicine);
        }
        return medicineList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT medId FROM Medicine";
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
