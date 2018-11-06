package Model;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Boss object in Mats platform worlds, he moves around and shoots bullets at Mats trying to kill him
 * The boss is killed when Mats his hitpoins run out
 *
 * @author Mathias Lundby, Jacob Larsen
 * @version 1.0 Build edited 12/5-18
 */
public class Boss extends EnemyObject {

    private Node player;
    public BossBullet bossBullet;
    private final String bossBulletImage = "Pictures/bossBullet.png";
    public AnimationTimer bossTimer;


    private float angle;


    private ArrayList<BossBullet> bulletList = new ArrayList<>();

    /**
     * Create boss at specific location
     * @param imgPath link to the image of the boss
     * @param x x coordinate
     * @param y y coordinate
     * @param hitPoints amount of hitpint
     * @param player the playerObject the boss is trying to kill
     */
    Boss(String imgPath, int x, int y, int hitPoints, Node player) {
        super(new ImageView(imgPath), x, y, hitPoints);

        this.getView().setTranslateX(x);
        this.getView().setTranslateY(y);
        this.defaultHp = hitPoints;
        this.isAlive = true;
        this.player = player;

        bossTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                bossMove();

            }
        };
        bossTimer.start();
    }


    /**
     * Makes the boss move in a circle
     */
    private void bossMove() {
        int radius = 150;  // radius

        angle += 0.03;
        this.getView().setTranslateX((int)(x + radius*Math.cos(angle)));
        this.getView().setTranslateY((int)(y - radius*Math.sin(angle)));
    }


    /**
     * Shoots a bullet towards the player Node
     */
    public void shootAimingBullet(){
        bossBullet = new BossBullet(bossBulletImage,this.getXposition() + 100, this.getYposition() + 100, 8, player, true);
        bulletList.add(bossBullet);
    }

    public void shootBullet() {
        bossBullet = new BossBullet(bossBulletImage,this.getXposition() + 100, this.getYposition() + 100, 8, player, false);
        bulletList.add(bossBullet);
    }

    /**
     * Resets the boss location, rotation, hitopints and starts his movement
     */
    public void bossReset(){
        bossTimer.start();
        this.getView().setTranslateX(x);
        this.getView().setTranslateY(y);
        this.getView().setRotate(0);
        bossTimer.start();
        setHitPoints(getDefaultHp());
    }


    public ArrayList<BossBullet> getBulletList() {
        return bulletList;
    }

}