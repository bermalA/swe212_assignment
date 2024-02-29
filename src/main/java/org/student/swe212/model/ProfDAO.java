package org.student.swe212.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfDAO {
    private final Connection connection;

    public ProfDAO(Connection connection) {this.connection = connection;}

    public void createProf(Prof prof) throws SQLException {
        String sql = "INSERT INTO professors (name, department) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, prof.getName());
        statement.setString(2, prof.getDepartment());
        statement.executeUpdate();
    }
    public void deleteProf(Long id ) throws  SQLException{
        if(id == null){
            throw new IllegalArgumentException("Prof ID cannot be null");
        }
        String sql = "DELETE FROM professors WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1,id);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted == 1){
            System.out.println("Prof deleted successfully");
        }else {
            System.out.println("Prof not found or deletion failed");
        }
    }
    public Long searchProfId(Prof prof) throws SQLException{
        Long id= -1L;
        String sql = "SELECT id FROM professors WHERE name= ? AND department= ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, prof.getName());
        statement.setString(2, prof.getDepartment());
        try(ResultSet rs = statement.executeQuery()){
            if(rs.next()){
                id= rs.getLong("id");
            }
        }return id;
    }
    public String updateProf(Prof prof, String id) throws SQLException{
        if(id==null){
            return "Prof ID cannot be null";
        }
        String sql = "UPDATE professors SET name = ?, department = ? WHERE id = ?";
        try(PreparedStatement statement= connection.prepareStatement(sql)){
            statement.setString(1, prof.getName());
            statement.setString(2, prof.getDepartment());
            statement.setString(3,id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0){
                return "Prof update failed";
            }
        }
        return "Prof updated successfully";
    }


}
