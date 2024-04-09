package hi.slidingpuzzle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static hi.slidingpuzzle.PuzzleController.isComplete;


public class Tile extends Pane {
    private boolean isBlank;
    public boolean winner=false;
    int originalLocation;
    private int x, y;
    private static Tile blankTile;
    private ImageView imageView;
    public Tile(Image image, boolean isBlank, int x, int y, double tileWidth, double tileHeight) {
        this.x = x;
        this.y = y;
        originalLocation=x+y+x*PuzzleController.tiles.length;
        this.isBlank = isBlank;
        setPrefSize(tileWidth, tileHeight);

        if (isBlank) {
            imageView = new ImageView(image);
            imageView.setFitWidth(tileWidth);
            imageView.setFitHeight(tileHeight);
            getChildren().add(imageView);
            blankTile = this;
        } else {
            imageView = new ImageView(image);
            imageView.setFitWidth(tileWidth);
            imageView.setFitHeight(tileHeight);
            getChildren().add(imageView);
        }

        setOnMouseClicked(event -> trySwap(false));
    }

    public void trySwap(boolean ai) {
        if(!ai){
            if(PuzzleController.winner) return;
        }
        if (this.isBlank || !isAdjacentToBlank()) return;


        Image tempImage = this.imageView.getImage();
        int tempLocation = this.originalLocation;
        this.imageView.setImage(blankTile.imageView == null ? null : blankTile.imageView.getImage());
        this.originalLocation= blankTile.originalLocation;
        if (blankTile.imageView != null) {
            blankTile.imageView.setImage(tempImage);
            blankTile.originalLocation=tempLocation;
        }

        this.isBlank = true;
        blankTile.isBlank = false;

        blankTile = this;
        if(!ai){
            isComplete(PuzzleController.tiles);
        }

    }

    private boolean isAdjacentToBlank() {
        int dx = Math.abs(this.x - blankTile.x);
        int dy = Math.abs(this.y - blankTile.y);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }
}
