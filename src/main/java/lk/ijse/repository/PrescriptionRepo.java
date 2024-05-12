package lk.ijse.repository;


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
        pstm.setObject(1,prescription.getPrescId());
        pstm.setObject(2,prescription.getDiagnosis());
        pstm.setObject(3, prescription.getVetId());


        return pstm.executeUpdate() > 0;
    }
    public static Prescription searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Prescription WHERE prescId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()){
            String P_id = rs.getString(1);
            String Diagnosis = rs.getString(2);
            String VetID = rs.getString(3);


            Prescription prescription = new Prescription(P_id,Diagnosis,VetID);

            return prescription;

        }
        return null;

    }
    public static boolean update(Prescription prescription) throws SQLException {
        String sql = "UPDATE Prescription SET diagnosis = ?, vetId = ? WHERE prescId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,prescription.getDiagnosis());
        pstm.setObject(2,prescription.getVetId());
        pstm.setObject(3,prescription.getPrescId());

        return pstm.executeUpdate() > 0;



    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Prescription WHERE prescId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }
    public static List<Prescription> getAll() throws SQLException {
        String sql = "SELECT * FROM Prescription";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Prescription> pList = new ArrayList<>();

        while (resultSet.next()) {
            String P_id = resultSet.getString(1);
            String Diagnosis = resultSet.getString(2);
            String VetID = resultSet.getString(3);


            Prescription prescription = new Prescription(P_id,Diagnosis,VetID);

            pList.add(prescription);
        }
        return pList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT prescId FROM Prescription";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }
}




