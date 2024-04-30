package lk.ijse.repository;

import lk.ijse.Model.Pet;
import lk.ijse.dbConnection.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class petRepo {


    public class PetRepo {
        public static boolean save(Pet pet) throws SQLException {
            String sql = "INSERT INTO Pet VALUES(?,?,?,?,?)";

            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, pet.getPetId());
            pstm.setInt(2, pet.getAge());
            pstm.setString(3, pet.getWeight());
            pstm.setString(4, pet.getBreed());
            pstm.setString(5, pet.getPetOwId());

            return pstm.executeUpdate() > 0;
        }

        public static Pet searchById(String id) throws SQLException {
            String sql = "SELECT * FROM Pet WHERE petId = ?";
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String petId = rs.getString(1);
                int age = rs.getInt(2);
                String weight = rs.getString(3);
                String breed = rs.getString(4);
                String petOwId = rs.getString(5);

                Pet pet = new Pet (petId, age, weight, breed, petOwId);

                return pet;
            }
            return null;
        }

        public static boolean update(Pet pet) throws SQLException {
            String sql = "UPDATE Pet SET age = ?, weight = ?, breed = ?, petOwId = ? WHERE petId = ?";
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, pet.getAge());
            pstm.setString(2, pet.getWeight());
            pstm.setString(3, pet.getBreed());
            pstm.setString(4, pet.getPetOwId());
            pstm.setString(5, pet.getPetId());
            return pstm.executeUpdate() > 0;
        }

        public static boolean delete(String id) throws SQLException {
            String sql = "DELETE FROM Pet WHERE petId = ?";
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, id);
            return pstm.executeUpdate() > 0;
        }

        public static List<Pet> getAll() throws SQLException {
            String sql = "SELECT * FROM Pet";
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
            ResultSet resultSet = pstm.executeQuery();
            List<Pet> petList = new ArrayList<>();
            while (resultSet.next()) {
                String petId = resultSet.getString(1);
                int age = resultSet.getInt(2);
                String weight = resultSet.getString(3);
                String breed = resultSet.getString(4);
                String petOwId = resultSet.getString(5);
                Pet pet = new Pet(petId, age, weight, breed, petOwId);
                petList.add(pet);
            }
            return petList;
        }

        public static List<String> getIds() throws SQLException {
            String sql = "SELECT petId FROM Pet";
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

}
