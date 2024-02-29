package org.student.swe212.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LectureDAO {
    private final Connection connection;

    public LectureDAO(Connection connection) {this.connection = connection;}

    public void createLecture(Lecture lect) throws SQLException {
        String sql = "INSERT INTO lectures (name, credit) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, lect.getName());
        statement.setString(2, Integer.toString(lect.getCredit()));
        statement.executeUpdate();
    }
    public void deleteLecture(Long id ) throws  SQLException{
        if(id == null){
            throw new IllegalArgumentException("Lecture ID cannot be null");
        }
        String sql = "DELETE FROM lectures WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1,id);
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted == 1){
            System.out.println("Lecture deleted successfully");
        }else {
            System.out.println("Lecture not found or deletion failed");
        }
    }
    public Long searchLectureId(Lecture lecture) throws SQLException{
        Long id= -1L;
        String sql = "SELECT id FROM lectures WHERE name= ? AND credit= ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, lecture.getName());
        statement.setInt(2, lecture.getCredit());
        try(ResultSet rs = statement.executeQuery()){
            if(rs.next()){
                id= rs.getLong("id");
            }
        }return id;
    }
    public String updateLecture(Lecture lect, String id) throws SQLException{
        if(id==null){
            return "Lecture ID cannot be null";
        }
        String sql = "UPDATE lectures SET name = ?, credit = ? WHERE id = ?";
        try(PreparedStatement statement= connection.prepareStatement(sql)){
            statement.setString(1, lect.getName());
            statement.setInt(2, lect.getCredit());
            statement.setString(3,id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0){
                return "Lecture update failed";
            }
        }
        return "Lecture updated successfully";
    }


}
