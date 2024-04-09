package hi.slidingpuzzle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import vinnsla.Difficulty;

import java.io.Console;
import java.util.Random;

public class PuzzleController {
    private int erfidleika;
    private double heildarstaerd=300.0;
    public static Tile[][] tiles;
    private Image selectedImage = null; // Class level variable
    @FXML
    private ListView<String> Listi;
    @FXML
    public GridPane Mynd;
    @FXML
    private Label myndHeiti;
    @FXML
    public void initialize() {
        Listi.getItems().addAll("Kirkjufell", "Gleym mér ei", "Zebra");


        Listi.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valinnListi(newValue);
        });
    }

    private void valinnListi(String item) {
        onLogin();
        String imagePath = switch (item) {
            case "Kirkjufell" -> "Myndir/kirkjufell.png";
            case "Gleym mér ei" -> "Myndir/gleym_mer_ei.png";
            case "Zebra" -> "Myndir/zebra.png";
            default -> null;
        };

        if (imagePath != null) {
            selectedImage = new Image(getClass().getResourceAsStream(imagePath),heildarstaerd,heildarstaerd,true,true);
            myndHeiti.setText(item);
            lagaGrid(erfidleika);
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

        double tileWidth = heildarstaerd / gridSize;
        double tileHeight = heildarstaerd / gridSize;
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
            tiles[x][y].trySwap(true);
        }

    }
    public static void isComplete(Tile[][] tiles) {
        int n = tiles.length;
        int counter=0;
        double total= Math.pow(n,2);
        for (int i = 0; i < n; i++) {
            int m = tiles[i].length;
            for (int j = 0; j < m; j++) {
                if (tiles[i][j].originalLocation == i+j) {
                    counter++;
                }
            }
        }
        System.out.println("réttir reitir: " + counter + " samtals reitir: " + total);
        if (counter==total) won();
    }
    private static void won(){ //skipta út fyrir öðrum popup?
        Alert win = new Alert(Alert.AlertType.INFORMATION);
        win.setTitle("Sigur!");
        win.setHeaderText("Til hamingju!");
        win.setContentText("Þú leystir púslið. Viltu ekki prófa aftur með fleiri reitum?");
        win.showAndWait();
    }
}

