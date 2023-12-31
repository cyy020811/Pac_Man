import bagel.Image;

/** Represents a wall that no entity can pass through.
 * @author Chi-Yuan Yang
 * @version 1.0
 */
public class Wall extends Entity {
    private final static Image WALL_IMAGE = new Image("res/wall.png");

    /** This method is used to create a Wall instance
     * @param x this is the x position of the Wall
     * @param y this is the y position of the Wall
     */
    public Wall(double x, double y) {
        super(WALL_IMAGE, x, y);
    }
}
