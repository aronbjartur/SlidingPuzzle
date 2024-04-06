package hi.slidingpuzzle;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class PuzzleController {

    @FXML
    private ListView<String> Listi;

    @FXML
    private GridPane Mynd;

    @FXML
    private Label myndHeiti;

    @FXML
    private void grid3x3() {
        lagaGrid(3);
    }

    @FXML
    private void grid4x4() {
        lagaGrid(4);
    }

    @FXML
    private void grid5x5() {
        lagaGrid(5);
    }


    @FXML
    public void initialize() {
        Listi.getItems().addAll("Kirkjufell", "Gleym mér ei", "Zebra");


        Listi.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valinnListi(newValue);
        });
    }

    private void valinnListi(String item) {
        String imagePath = null;
        if ("Kirkjufell".equals(item)) {
            imagePath = "Myndir/kirkjufell.png";
        } else if ("Gleym mér ei".equals(item)) {
            imagePath = "Myndir/gleym_mer_ei.png";
        } else if ("Zebra".equals(item)) {
            imagePath = "Myndir/zebra.png";
        }

        if (imagePath != null) {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(300);
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);

            Mynd.getChildren().clear();
            GridPane.setColumnSpan(imageView, 3);
            GridPane.setRowSpan(imageView, 3);
            Mynd.add(imageView, 0, 0);

            myndHeiti.setText(item);
        }
    }

    public void lagaGrid(int gridSize) {
        Mynd.getChildren().clear();
        Mynd.getColumnConstraints().clear();
        Mynd.getRowConstraints().clear();

        double cellSize = 300.0 / gridSize;

        for (int i = 0; i < gridSize; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(cellSize);
            RowConstraints rowConstraints = new RowConstraints(cellSize);

            Mynd.getColumnConstraints().add(columnConstraints);
            Mynd.getRowConstraints().add(rowConstraints);
        }
    }



}

