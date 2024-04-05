module hi.slidingpuzzle {
    requires javafx.controls;
    requires javafx.fxml;


    opens hi.slidingpuzzle to javafx.fxml;
    exports hi.slidingpuzzle;
}