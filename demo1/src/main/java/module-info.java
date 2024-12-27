module org.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires itextpdf;


    opens org.example.demo1 to javafx.fxml;
    exports org.example.demo1;
}