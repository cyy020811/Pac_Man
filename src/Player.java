import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;

public class Player extends Entity {
    private final static Image PAC_CLOSE_IMAGE = new Image("res/pac.png");
    private final static Image PAC_OPEN_IMAGE = new Image("res/pacOpen.png");
    private double rotation = 0;
    private Point startPos;
    private int speed = 3;

    public Player(double x, double y) {
        super(PAC_CLOSE_IMAGE, x, y);
        startPos = new Point(x, y);
    }

    // Rotate the PacMan according to the direction of movement
    public void setRotation(double rotation) {
        this.rotation = rotation * Math.PI / 180;
    }

    public void draw(int frame) {
        DrawOptions drawOptions = new DrawOptions();
        // Switch between open and close images every 15 frames and rotate the PacMan
        if ((frame / 15) % 2 == 0) {
            PAC_CLOSE_IMAGE.drawFromTopLeft(topLeft().x, topLeft().y, drawOptions.setRotation(rotation));
        } else {
            PAC_OPEN_IMAGE.drawFromTopLeft(topLeft().x, topLeft().y, drawOptions.setRotation(rotation));
        }
    }

    // Move the PacMan to the new position
    public void move(double x, double y) {
        setX(x);
        setY(y);
        getHitbox().moveTo(new Point(x, y));
    }

    // Return to the starting position
    public void returnStart() {
        move(startPos.x, startPos.y);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
