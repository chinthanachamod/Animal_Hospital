package lk.ijse.repository;

import lk.ijse.Model.Appointment;
import lk.ijse.dbConnection.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppoinmentRepo {
    public static boolean save(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO Appointment VALUES(?,?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, appointment.getAppId());
        pstm.setDate(2, (Date) appointment.getDate());
        pstm.setString(3, appointment.getTime());
        pstm.setString(4, appointment.getPetOwId());
        pstm.setString(5, appointment.getVetId());

        return pstm.executeUpdate() > 0;
    }

    public static Appointment searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Appointment WHERE appId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            String appId = rs.getString(1);
            java.util.Date date = rs.getDate(2);
            String time = rs.getString(3);
            String petOwId = rs.getString(4);
            String vetId = rs.getString(5);

            return new Appointment(appId, date, time, petOwId, vetId);
        }
        return null;
    }

    public static boolean update(Appointment appointment) throws SQLException {
        String sql = "UPDATE Appointment SET date = ?, time = ?, petOwId = ?, vetId = ? WHERE appId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setDate(1, new java.sql.Date(appointment.getDate().getTime()));
        pstm.setString(2, appointment.getTime());
        pstm.setString(3, appointment.getPetOwId());
        pstm.setString(4, appointment.getVetId());
        pstm.setString(5, appointment.getAppId());
        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Appointment WHERE appId = ?";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);
        return pstm.executeUpdate() > 0;
    }

    public static List<Appointment> getAll() throws SQLException {
        String sql = "SELECT * FROM Appointment";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        List<Appointment> appointmentList = new ArrayList<>();
        while (resultSet.next()) {
            String appId = resultSet.getString(1);
            java.util.Date date = resultSet.getDate(2);
            String time = resultSet.getString(3);
            String petOwId = resultSet.getString(4);
            String vetId = resultSet.getString(5);
            Appointment appointment = new Appointment(appId, date, time, petOwId, vetId);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT appId FROM Appointment";
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