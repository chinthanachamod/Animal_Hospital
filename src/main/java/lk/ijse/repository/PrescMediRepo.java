package lk.ijse.repository;

import lk.ijse.Model.petMediDetail;
import lk.ijse.dbConnection.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescMediRepo {
    public static boolean save(petMediDetail ps) throws SQLException {
        String sql = "INSERT INTO PetMedDetail  VALUES(?,?,?)";
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, ps.getMedicine_ID());
        pstm.setString(2, ps.getP_ID());
        pstm.setString(3, String.valueOf(ps.getQTY()));


        return pstm.executeUpdate() > 0;

    }
    public static List<petMediDetail> getAll() throws SQLException {
        String sql = "SELECT * FROM PetMedDetail";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<petMediDetail> PSList = new ArrayList<>();

        while (resultSet.next()) {
            String P_ID = resultSet.getString(1);
            String M_Id = resultSet.getString(2);
            int Qty = Integer.parseInt(resultSet.getString(3));


            petMediDetail ps = new petMediDetail(P_ID,M_Id,Qty);
            PSList.add(ps);
        }
        return PSList;
    }
}
