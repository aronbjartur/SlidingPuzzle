package hi.slidingpuzzle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import vinnsla.Difficulty;

import java.util.Objects;
import java.util.Random;

public class PuzzleController {


    private int elapsedTime = 0;
    private Timeline gameTimer;
    @FXML
    private Label Timi;
    private int erfidleika=0;
    public static boolean winner =false;
    private double heildarstaerd=300.0;
    public static Tile[][] tiles;
    private Image selectedImage = null;
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
            if (newValue != null) {
                valinnListi(newValue);
                Platform.runLater(() -> {
                    Listi.getSelectionModel().clearSelection();
                });
            }
        });
    }

    private void valinnListi(String item) {
        onLogin();

        if (erfidleika==0){
            return;
        }
        String imagePath = switch (item) {
            case "Kirkjufell" -> "Myndir/kirkjufell.png";
            case "Gleym mér ei" -> "Myndir/gleym_mer_ei.png";
            case "Zebra" -> "Myndir/zebra.png";
            default -> null;
        };

        if (gameTimer != null) {
            gameTimer.stop();
        }
        elapsedTime = 0;

        if (imagePath != null) {
            selectedImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)),heildarstaerd,heildarstaerd,true,true);
            myndHeiti.setText(item);
            lagaGrid(erfidleika);
        }
        setupGameTimer();
    }
    public void onLogin() {
        erfidleika=0;
        DifficultyDialog a = new DifficultyDialog();
        Difficulty difficulty = a.getDifficultyValue();
        if (difficulty != null) {
            erfidleika=difficulty.getValue();
            winner=false;
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
                if (tiles[i][j].originalLocation == i+j+i*n) {
                    counter++;
                }
            }
        }
        if (counter==total) won();
    }

    private void updateGameTime() {
        if (!winner) {
            elapsedTime++;
            int minutes = elapsedTime / 60;
            int seconds = elapsedTime % 60;
            Timi.setText(String.format("%02d:%02d", minutes, seconds));
        } else {
            gameTimer.stop();
        }
    }


    private void setupGameTimer() {
        gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateGameTime()));
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();
    }

    private static void won(){
        winner=true;
        Alert win = new Alert(Alert.AlertType.INFORMATION);
        win.setTitle("Sigur!");
        win.setHeaderText("Til hamingju!");
        win.setContentText("Þú leystir púslið. Viltu ekki prófa aftur með fleiri reitum?");
        win.showAndWait();



    }
}
