package hi.slidingpuzzle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import vinnsla.Difficulty;

import java.util.Random;

public class PuzzleController {
    private int erfidleika;
    private Tile[][] tiles;
    @FXML
    private ListView<String> Listi;

    @FXML
    private GridPane Mynd;

    @FXML
    private Label myndHeiti;

    @FXML
    public void initialize() {
        Listi.getItems().addAll("Kirkjufell", "Gleym mér ei", "Zebra");


        Listi.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valinnListi(newValue);
        });
    }

    private Image selectedImage = null; // Class level variable

    private void valinnListi(String item) {
        onLogin();
        String imagePath = switch (item) {
            case "Kirkjufell" -> "Myndir/kirkjufell.png";
            case "Gleym mér ei" -> "Myndir/gleym_mer_ei.png";
            case "Zebra" -> "Myndir/zebra.png";
            default -> null;

        };

        if (imagePath != null) {
            selectedImage = new Image(getClass().getResourceAsStream(imagePath));
            myndHeiti.setText(item);
            lagaGrid(erfidleika); // má taka þetta út seinna
        }
    }
    public void onLogin() {
        DifficultyDialog a = new DifficultyDialog();
        Difficulty difficulty = a.getDifficultyValue();
        if (difficulty != null) {
            erfidleika=difficulty.getValue();
        } else {
            System.out.println("notandi hætti við");
        }
    }


    public void lagaGrid(int gridSize) {
        Mynd.getChildren().clear();
        Mynd.getColumnConstraints().clear();
        Mynd.getRowConstraints().clear();

        double heildarStaerd = 300.0;
        double tileWidth = heildarStaerd / gridSize;
        double tileHeight = heildarStaerd / gridSize;
        tiles=new Tile[gridSize][gridSize];


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
                tiles[x][y]=tile;
            }
        }
        Random rand = new Random();
        for(int i = 0; i < 10000; i++){
            int x = rand.nextInt(gridSize);
            int y = rand.nextInt(gridSize);
            tiles[x][y].trySwap();
        }
    }







}

