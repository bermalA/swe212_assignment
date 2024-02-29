module org.student.swe212 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.student.swe212 to javafx.fxml;
    exports org.student.swe212;
}