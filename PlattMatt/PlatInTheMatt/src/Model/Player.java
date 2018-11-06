package Model;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The player object in the game controlled by the player
 *
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public class Player extends GameObject {

    public PlayerBullet bullet;
    public AudioClip jumpSound;

    private boolean canJump = false;
    private boolean isMovingRight, isMovingLeft;
    private boolean shootingRight = true;

    private final String bulletImage = "Pictures/bullet.png";


    private ArrayList<PlayerBullet> bulletList = new ArrayList<>();


    /**
     * Create player at immutable starting location
     * Uses the imageView from the GameObject class to create an image
     */
    Player() {
        super(new ImageView(new Image("Pictures/avatar.png")));

        final int xStartPosition = 50;
        final int yStartPosition = 490;
        this.getView().setTranslateX(xStartPosition);
        this.getView().setTranslateY(yStartPosition);

        //Hoppelyd
        String soundPath = "src/Sounds/jump.wav";
        jumpSound = new AudioClip(Paths.get(soundPath).toUri().toString());//this.getClass().getResource(soundPath).toString());
        jumpSound.setVolume(0.1);
    }

    protected void moveLeft() {
        isMovingLeft = true;
        isMovingRight = false;
        shootingRight = false;
    }

    protected void moveRight(){
        isMovingRight = true;
        isMovingLeft = false;
        shootingRight = true;
    }

    protected void standingStill(){
        isMovingRight = false;
        isMovingLeft = false;
    }

    /**
     * jumps player
     */
    public void jump(){
        if(canJump) {
            jumpSound.play();
            this.setVelocity(new Point2D(0, -20));
            canJump = false;
        }
    }


    /**
     * making if possible to move around on the platforms
     * @param platforms list of the platfrom
     */
    void updateMovement(ArrayList<Platform> platforms) {
        if (isMovingRight) {
            this.getView().setTranslateX(this.getXposition() + 6);
        }
        if (isMovingLeft) {
            this.getView().setTranslateX(this.getXposition() - 6);
        }
        for (Platform platform : platforms) {
            Bounds bounds = platform.getLayoutBounds();
            if (this.getView().getBoundsInParent().intersects(bounds)) {
                if (isMovingRight) {
                    this.getView().setTranslateX(bounds.getMinX() - 40.01);
                }
                if (isMovingLeft) {
                    this.getView().setTranslateX(bounds.getMaxX() + 0.01);
                }
            }
        }
        if (this.getXposition() < 0) {
            this.getView().setTranslateX(0);
        }

        if (this.getVelocity().getY() < 10) {
            this.setVelocity(this.getVelocity().add(0, 1));
        }
        this.getView().setTranslateY(this.getYposition() + this.getVelocity().getY());

        for (Platform platform : platforms) {
            Bounds bounds = platform.getLayoutBounds();
            if (this.getView().getBoundsInParent().intersects(bounds)) {
                if(this.getVelocity().getY() > 0){
                    this.getView().setTranslateY(bounds.getMinY()  -40.01);
                    canJump = true;
                    this.setVelocity(new Point2D(0,0));
                }
                else if (this.getVelocity().getY() < 0){
                    this.getView().setTranslateY(bounds.getMaxY() + 0.01);
                    this.setVelocity(new Point2D(0,0));
                }
            }
        }
    }

    /**
     * Tests if the playes is colliding with spikes
     * @param spikes list of spikes
     * @return if the player is colliding with spikes
     */
    public boolean spikeCollision(ArrayList<Spikes> spikes) {
        for (Spikes spike : spikes) {
            if (this.getView().getBoundsInParent().intersects(spike.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Test if the player has fallen out of the map
     * @return if the player has fallen out of the map
     */
    protected boolean fall(){
        return this.getYposition() < 800;
    }

    protected void playerReset() {
        this.getView().setTranslateX(50);
        this.getView().setTranslateY(490);
        this.setVelocity(new Point2D(0,0));
        canJump = false;
    }

    /**
     * creates a playerBullet object
     */
    public void shootBullet(){
        bullet = new PlayerBullet(bulletImage, this.getXposition() + 12, this.getYposition() + 12, 10, shootingRight);
        bulletList.add(bullet);
    }

    public ArrayList<PlayerBullet> getBulletList() {
        return bulletList;
    }
}