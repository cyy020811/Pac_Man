import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;

public class Player extends Entity {
    private final static Image PAC_CLOSE_IMAGE = new Image("res/pac.png");
    private final static Image PAC_OPEN_IMAGE = new Image("res/pacOpen.png");
    private double rotation = 0;
    private Point startPos;
    private int speed = 3;

    /** This method is used to create a Player instance
     * @param x this is the x position of the Player
     * @param y this is the y position of the Player
     */
    public Player(double x, double y) {
        super(PAC_CLOSE_IMAGE, x, y);
        startPos = new Point(x, y);
    }

    /** This method is used to rotate the PacMan according to the direction of movement
     * @param rotation this is the direction of the Pacman
     */
    public void setRotation(double rotation) {
        this.rotation = rotation * Math.PI / 180;
    }

    /** This method is used to rotate the PacMan according to the direction of movement
     * @param frame this is the direction of the PacMan
     */
    public void draw(int frame) {
        DrawOptions drawOptions = new DrawOptions();
        // Switch between open and close images every 15 frames and rotate the PacMan
        if ((frame / 15) % 2 == 0) {
            PAC_CLOSE_IMAGE.drawFromTopLeft(topLeft().x, topLeft().y, drawOptions.setRotation(rotation));
        } else {
            PAC_OPEN_IMAGE.drawFromTopLeft(topLeft().x, topLeft().y, drawOptions.setRotation(rotation));
        }
    }

    /** This method is used to move the PacMan to the new position
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void move(double x, double y) {
        setX(x);
        setY(y);
        getHitbox().moveTo(new Point(x, y));
    }

    /** This method is used to return the PacMan to the starting position
     */
    public void returnStart() {
        move(startPos.x, startPos.y);
    }

    /** Set the speed of the PacMan according to the frenzy mode
     * @param frenzyMode if it is frenzy mode
     */
    public void setSpeed(boolean frenzyMode) {
        if (frenzyMode) speed = 4;
        else speed = 3;
    }

    /** Get the speed of the PacMan
     * @return int the speed
     */
    public int getSpeed() {
        return speed;
    }
}
