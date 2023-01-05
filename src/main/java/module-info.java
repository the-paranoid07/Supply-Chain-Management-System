module com.example.supplychianmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.supplychianmanagementsystem to javafx.fxml;
    exports com.example.supplychianmanagementsystem;
}