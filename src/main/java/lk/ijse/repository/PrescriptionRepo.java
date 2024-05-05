package lk.ijse.repository;

import lk.ijse.Model.PetOwner;
import lk.ijse.Model.Prescription;
import lk.ijse.dbConnection.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepo {
    public static boolean save(Prescription prescription) throws SQLException {
        String sql = "INSERT INTO Prescription VALUES(?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, prescription.getPrescId());
        pstm.setString(2, prescription.getDiagnosis());
        pstm.setString(3, prescription.getVetId());

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getVetIDs() throws SQLException {
        String sql = "SELECT vetId FROM Veterinarian";

        Connection connection = DbConnection.getInstance().getConnection();

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }
}
