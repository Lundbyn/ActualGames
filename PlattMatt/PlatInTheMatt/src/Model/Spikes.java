package Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Spikes that Mat can move on
 *
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public class Spikes extends Platform {


    /**
     * Create Spikes at specific location
     * @param x x coordinate
     * @param y y coordinate
     * @param width width of the rectangle
     * @param height height of the rectangle
     * @param imgPath image-link to the image
     */
    Spikes(int x, int y, int width, int height, String imgPath) {
        super(x, y, width, height, imgPath);

        this.imgView = new ImageView(new Image(imgPath));
        this.imgView.setX(x);
        this.imgView.setY(y);
    }
}