package hi.slidingpuzzle;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
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

    private Image selectedImage = null; // Class level variable

    private void valinnListi(String item) {
        String imagePath = switch (item) {
            case "Kirkjufell" -> "Myndir/kirkjufell.png";
            case "Gleym mér ei" -> "Myndir/gleym_mer_ei.png";
            case "Zebra" -> "Myndir/zebra.png";
            default -> null;
        };

        if (imagePath != null) {
            selectedImage = new Image(getClass().getResourceAsStream(imagePath));
            myndHeiti.setText(item);
            lagaGrid(3); // má taka þetta út seinna
        }
    }



    public void lagaGrid(int gridSize) {
        Mynd.getChildren().clear();
        Mynd.getColumnConstraints().clear();
        Mynd.getRowConstraints().clear();

        double heildarStaerd = 300.0;
        double tileWidth = heildarStaerd / gridSize;
        double tileHeight = heildarStaerd / gridSize;


        for (int i = 0; i < gridSize; i++) {
            Mynd.getColumnConstraints().add(new ColumnConstraints(tileWidth));
            Mynd.getRowConstraints().add(new RowConstraints(tileHeight));
        }

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                boolean isBlank = (x == gridSize - 1) && (y == gridSize - 1);
                Image tileImage = isBlank ? null : new WritableImage(selectedImage.getPixelReader(), (int) (x * tileWidth), (int) (y * tileHeight), (int) tileWidth, (int) tileHeight);
                Tile tile = new Tile(tileImage, isBlank, x, y, tileWidth, tileHeight);
                Mynd.add(tile, x, y);
            }
        }
    }







}

