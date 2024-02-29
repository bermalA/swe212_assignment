package org.student.swe212;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.student.swe212.model.Lecture;
import org.student.swe212.model.LectureDAO;
import org.student.swe212.model.Prof;
import org.student.swe212.model.ProfDAO;

import java.sql.*;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField departmentField;
    @FXML
    private Label lectureId;
    @FXML
    private TextField lectureName;
    @FXML
    private TextField lectureCredit;
    @FXML
    private TableView tableView;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/swe212";
    private static final String DB_USERNAME = "user";
    private static final String DB_PASSWORD = "password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    @FXML
    protected void initialize() {
    }
   /* public void populateTable() throws SQLException {
        String sql = "SELECT p.name AS professorName, l.name AS lectureName, l.startDate, l.endDate " +
                "FROM professor_lectures pl " +
                "INNER JOIN professors p ON pl.professor_id = p.id " +
                "INNER JOIN lectures l ON pl.lecture_id = l.id";
        Connection connection = getConnection();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                ObservableList<Lecture> lectures = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    Lecture lecture = new Lecture(
                            resultSet.getString("professorName"),
                            resultSet.getString("lectureName"),
                            resultSet.getDate("startDate"),
                            resultSet.getDate("endDate")
                    );
                    lectures.add(lecture);
                }
                tableView.setItems(lectures);
            }
        }
    }*/

    @FXML
    protected void onActionFetchProf() {
        Prof prof = new Prof(null, nameField.getText(), departmentField.getText());

        try (Connection connection = getConnection()) {
            ProfDAO dao = new ProfDAO(connection);
            Long id = dao.searchProfId(prof);
            if (id != -1L) {
                idField.setText(Long.toString(id));
                System.out.println("Found the id: " + id);
            } else {
                System.out.println("Professor not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching professor: " + e.getMessage());
        }
    }
    @FXML
    protected void onActionFetchLecture(){
        Lecture lecture = new Lecture(null,lectureName.getText(), Integer.parseInt(String.valueOf(lectureCredit.getText())) );
        try(Connection connection = getConnection()){
            LectureDAO dao = new LectureDAO(connection);
            Long id = dao.searchLectureId(lecture);
            if(id != -1L){
                lectureId.setText(Long.toString(id));
                System.out.println("Found the id: "+ id);
            }else{
                System.out.println("Lecture not found");
            }
        }catch (SQLException e){
            System.out.println("Error fetching lecture: "+ e.getMessage());
        }
    }
    @FXML
    protected void onCreateActionProf() {
        Prof newProf = new Prof(null, nameField.getText(), departmentField.getText());
        try (Connection connection = getConnection()) {
            ProfDAO dao = new ProfDAO(connection);
            dao.createProf(newProf);
            System.out.println("Professor created successfully");
        } catch (SQLException e) {
            System.out.println("Error creating professor: " + e.getMessage());
        }
    }
    @FXML
    protected void onCreateActionLecture(){
        Lecture lecture = new Lecture(null, lectureName.getText(), Integer.parseInt(String.valueOf(lectureCredit.getText())));
        try(Connection connection = getConnection()){
            LectureDAO dao = new LectureDAO(connection);
            dao.createLecture(lecture);
            System.out.println("Lecture created successfully");
        }catch (SQLException e) {
            System.out.println("Error creating lecture:  "+ e.getMessage());
        }
    }
    @FXML
    protected void onUpdateActionProf() {
        Prof prof = new Prof(null, nameField.getText(), departmentField.getText());
        try (Connection connection = getConnection()) {
            ProfDAO dao = new ProfDAO(connection);
            String id = idField.getText();
            dao.updateProf(prof, id);
            System.out.println("Professor updated successfully");
        } catch (SQLException e) {
            System.out.println("Error updating professor: " + e.getMessage());
        }
    }
    @FXML
    protected void onUpdateActionLecture() {
        Lecture lecture = new Lecture(null, lectureName.getText(), Integer.parseInt(String.valueOf(lectureCredit.getText())));
        try (Connection connection = getConnection()) {
            LectureDAO dao = new LectureDAO(connection);
            String id = lectureId.getText();
            dao.updateLecture(lecture, id);
            System.out.println("Lecture updated successfully");
        } catch (SQLException e) {
            System.out.println("Error updating lecture: " + e.getMessage());
        }
    }
    @FXML
    protected void onDeleteActionProf() {
        String idStr = idField.getText();
        try (Connection connection = getConnection()) {
            ProfDAO dao = new ProfDAO(connection);
            Long id = Long.parseLong(idStr);
            dao.deleteProf(id);
            System.out.println("Professor deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid ID format. Please enter a valid number.");
        } catch (SQLException e) {
            System.out.println("Error deleting professor: " + e.getMessage());
        }
    }
    @FXML
    protected  void onDeleteActionLecture(){
        String idStr = lectureId.getText();
        try(Connection connection = getConnection()){
            LectureDAO dao = new LectureDAO(connection);
            Long id = Long.parseLong(idStr);
            dao.deleteLecture(id);
            System.out.println("Lecture deleted successfully");
        }catch (NumberFormatException e){
            System.out.println("Error: Invalid ID format. Please enter a valid number.");
        } catch (SQLException e){
            System.out.println("Error deleting lecture" + e.getMessage());
        }
    }
    @FXML
    protected void onCloseAction() {
        Platform.exit();
    }
}
