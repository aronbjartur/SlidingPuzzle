package hi.slidingpuzzle;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;

public class Tile extends Pane {
    private boolean isBlank;
    private int x, y;
    private static Tile blankTile;
    private ImageView imageView;

    public Tile(Image image, boolean isBlank, int x, int y, double tileWidth, double tileHeight) {
        this.x = x;
        this.y = y;
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

        setOnMouseClicked(event -> trySwap());
    }

    public void trySwap() {
        if (this.isBlank || !isAdjacentToBlank()) return;

        Image tempImage = this.imageView.getImage();
        this.imageView.setImage(blankTile.imageView == null ? null : blankTile.imageView.getImage());
        if (blankTile.imageView != null) {
            blankTile.imageView.setImage(tempImage);
        }

        this.isBlank = true;
        blankTile.isBlank = false;

        blankTile = this;
    }

    private boolean isAdjacentToBlank() {
        int dx = Math.abs(this.x - blankTile.x);
        int dy = Math.abs(this.y - blankTile.y);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }


}
