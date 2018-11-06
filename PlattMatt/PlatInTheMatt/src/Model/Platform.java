package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


/**
 * Platform the player is walking/ sliding on
 *
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public class Platform extends Rectangle {

    ImageView imgView;

    /**
     * Create platform at specific location
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param imgPath link to the image of the platform
     */
    Platform(int x, int y, int width, int height, String imgPath) {
        super(x, y, width, height);
        this.imgView = new ImageView(new Image(imgPath));
        this.imgView.setX(x);
        this.imgView.setY(y);
    }

    /**
     * @return the imageView making it possible to add it to the root
     */
    public ImageView getImgView() {
        return imgView;
    }
}